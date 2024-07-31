import os
import requests
requests.packages.urllib3.disable_warnings()

output_file_name = 'out/output.txt'
headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + os.getenv('TOKEN', ''),
}
json_data = {'query': '\\'}

print('Checking token...')
if os.getenv('TOKEN', '') == '':
    print('TOKEN is not set. Follow README to export TOKEN before running script.')
    exit(1)
print('Token has been set.')

print('Reaching out to GIC for raw data...')
response = requests.post(
    'https://pl-gic.childrens.harvard.edu/picsure/search/64633639-3963-3962-2d30-3633312d3131',
    headers=headers,
    json=json_data,
    verify=False,
)

if response.status_code != 200:
    print('Bad response code. Exiting.')
    print(response.json())
    exit(1)
print('Successfully pulled raw data.')

print('Parsing response and pulling out paths...')
phenotypes = response.json().get('results').get('phenotypes')
print('Found {paths} paths in response.'.format(paths=len(phenotypes.keys())))

print('Pruning leaf nodes...')
potential_leaf_nodes = {}
for concept in sorted(phenotypes.keys()):
    segments = concept.split('\\')
    for i, segment in enumerate(segments):
        potential_leaf_nodes.pop('\\'.join(segments[0:i]) + '\\', None)
    potential_leaf_nodes[concept] = phenotypes.get(concept)
print('Found {leafs} viable leaf nodes'.format(leafs=len(potential_leaf_nodes)))

print('Printing leaf nodes in human readable format to {out}'.format(out=output_file_name))
with open(output_file_name, 'a') as output:
    for leaf_name in potential_leaf_nodes:
        leaf = potential_leaf_nodes[leaf_name]
        name = leaf.get('name').split('\\')[-2]
        to_write = ""
        # continuous
        if leaf.get('categoryValues') is None:
            to_write = """
{name}
    path: {path}
    range: [{min}, {max}]
    observations: {obs}
    sites: {sites}
            """.format(
                name=name, path=leaf.get('name'),
                min=leaf.get('min'), max=leaf.get('max'),
                obs=leaf.get('observationCount'), sites=leaf.get('resourceAvailability')
            )
        # categorical
        else:
            to_write = """
{name}
    path: {path}
    values: {values}
    observations: {obs}
    sites: {sites}
            """.format(
                name=name, path=leaf.get('name'),
                values=leaf.get('categoryValues'),
                obs=leaf.get('observationCount'), sites=leaf.get('resourceAvailability')
            )
        output.write(to_write)

print('Done.')
