#!/bin/bash

# script renames files with ' ' spaces to underscores
# in the current directory (.)

for f in ./*; 
do mv "$f" "${f/ /_}"; 
done

# bug - only does the first instance of ' '
# --> rerunning the script works but gets a lot of warnings of files without ' '.

