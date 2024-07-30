curl -k -o $INPUT_DIR 'https://pl-gic.childrens.harvard.edu//picsure/search/64633639-3963-3962-2d30-3633312d3131' --compressed -X POST \
-H 'Content-Type: application/json' \
-H "Authorization: Bearer $TOKEN" \
--data '{"query":"\\"}'