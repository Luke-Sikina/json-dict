# GIC Dictionary Translator

Standalone translator for GIC Data Dictionary

## Requirements

- Python (this seems to work on 2.x and 3.x)
  - Make sure `requests` is installed
- Access to GIC

## Usage

### Set API token

1. Log into GIC
2. Copy your `Personalized Access Token` from the `User Profile` tab in GIC.

Don't share this token with anyone. They can use it to impersonate you.

### Run the script

1. Open a terminal (I'm assuming OSX/*nix/WSL)
2. Set your token: `export TOKEN=<the token value you copied>`  
    You can sanity check this by making sure `echo $TOKEN` returns your pasted text
3. Clone this repo: `git clone https://github.com/Luke-Sikina/json-dict.git`
4. Cd into the repo: `cd json-dict`
5. Run the script: `python pull_and_convert.py` 
6. The output will look something like this:
```
Checking token...
Token has been set.
Reaching out to GIC for raw data...
Successfully pulled raw data.
Parsing response and pulling out paths...
Found 3XXXX paths in response.
Pruning leaf nodes...
Found 2XXXX viable leaf nodes
Printing leaf nodes in human readable format to out/output.txt
Done.
```

7. The dictionary will be in `out/output.txt`

### Refining the output

If you want to drill down to a particular site, you can run this terminal
command to filter the output:

```shell
cat out/output.txt | grep -B 4 'BCH' >> out/bch.txt
```