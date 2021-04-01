## Miscellaneous

This part is intended for you to generate statistics virtually in any language. You create your data using the standard described [here](http://localhost:8080/swagger-ui/index.html#/statistics-controller-impl/createStatisticsUsingPOST), run the server project and post to `http://localhost:8080/statistics/create`.

_Note_: For SQL, you can use the resource folder in the server project, which is probably easier, but you can use your favorite language to query the database and post to that link anyways.

### TSV usage

WCA lets available a tsv file with relevant information. You can create your statistics by analysing it. To make things easier, we let available a script to sort the tsv (and append a date) since the original tsv is not necessarily sorted.

You can download the tsv and sort it with the `scripts/get_tsv_export.sh`.

```
chmod +x scripts/get_tsv_export.sh
./scripts/get_tsv_export.sh
```

You will need to have [pandas](https://pandas.pydata.org/) installed, which can by following instructions in the README inside `misc/python`.
