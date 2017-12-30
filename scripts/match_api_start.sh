#!/bin/bash
server_folder_path="DIR PATH"
match_api_jar="005_api_form_matchup_table-0.0.1-SNAPSHOT.jar"
match_api_jar_path="${server_folder_path}${match_api_jar}"

cd ${server_folder_path}
chmod 755 ${match_api_jar_path}
java -Xms256M -Xmx256M -jar ${match_api_jar_path}
