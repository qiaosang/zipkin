#!/bin/bash

cd ../
echo " *** Removing tmp folders and metadata recursively from"
pwd

declare -a tmp_folders=( '.settings' 'target' 'bin' '.idea' '.classpath' '.apt_generated' 'log')
declare -a tmp_files=( '.project' '*.iml' '.classpath' '.factorypath' )

echo "Start removing ..."

for folder in ${tmp_folders[@]}; do
	rm -rf `find . -type d -name "${folder}"`
	echo " *** Removed tmp folder "${folder}""
done

for file in ${tmp_files[@]}; do
	find ./ -iname "${file}" -exec rm '{}' ';'
	echo " *** Removed tmp file "${file}""
done
