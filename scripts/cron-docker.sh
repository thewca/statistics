#!/bin/bash

# This will run inside a container

# Clone the repo
git clone https://github.com/thewca/statistics.git

# Enter it
cd statistics

# Execute the script. This is not in the same folder as the current script since it's a new clone
source scripts/cron.sh
