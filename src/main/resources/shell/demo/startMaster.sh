#!/bin/bash

gamesPath="Steam/steamapps/common/Don't Starve Together Dedicated Server/bin"
cd "$HOME"
cd "$gamesPath"
run_shared=(./dontstarve_dedicated_server_nullrenderer)
run_shared+=(-console)
run_shared+=(-cluster {{CLUSTER_ID}})
run_shared+=(-monitor_parent_process $)

"${run_shared[@]}" -shard Master


#!/bin/bash

gamesPath="Steam/steamapps/common/Don't Starve Together Dedicated Server/bin"
cd "$HOME"
cd "$gamesPath"
run_shared=(./dontstarve_dedicated_server_nullrenderer)
run_shared+=(-console)
run_shared+=(-cluster {{CLUSTER_ID}})
run_shared+=(-monitor_parent_process $)

"${run_shared[@]}" -shard Caves