#!/bin/bash

# server must be running

echo "Generate python statistics"
for filename in misc/python/statistics/*.py; do
    echo $filename
    to_execute="python3 -m ${filename%.*}"
    module=${to_execute//[\/]/.}
    ($module)
done
echo "Python statistics generated"
