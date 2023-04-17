#!/bin/sh

#### 根据机器情况自动适配 JVM Memory MIN/MAX
JVM_XMX_MEM_RATIO=80
JVM_XMN_MEM_RATIO=50
common_calc() {
  local formula="$1"
  shift
  echo "$@" | awk '
    function ceil(x) {
      return x % 1 ? int(x) + 1 : x
    }
    function log2(x) {
      return log(x)/log(2)
    }
    function max2(x, y) {
      return x > y ? x : y
    }
    function round(x) {
      return int(x + 0.5)
    }
    {print '"int(${formula})"'}
  '
}
jvm_calc_mem() {
  local max_mem="$1"
  local fraction="$2"

  local val=$(common_calc 'round($1*$2/100/1048576)' "${max_mem}" "${fraction}")
  echo "${val}"
}
system_env_max_memory() {
  local mem_file="/sys/fs/cgroup/memory/memory.limit_in_bytes"
  if [ -r "${mem_file}" ]; then
    local max_mem_cgroup="$(cat ${mem_file})"
    local max_mem_meminfo_kb="$(cat /proc/meminfo | awk '/MemTotal/ {print $2}')"
    local max_mem_meminfo="$(expr $max_mem_meminfo_kb \* 1024)"
    if [ ${max_mem_cgroup:-0} != -1 ] && [ ${max_mem_cgroup:-0} -lt ${max_mem_meminfo:-0} ]
    then
      echo "${max_mem_cgroup}"
    else
      echo "${max_mem_meminfo}"
    fi
  fi
}

mem_limit="$(system_env_max_memory)"
if [ -n "${mem_limit}" ]; then
   export MAX_MEMORY="${mem_limit}"
fi

xmx_mb=$(jvm_calc_mem "${MAX_MEMORY}" "${JVM_XMX_MEM_RATIO}")
xmn_mb=$(jvm_calc_mem "${MAX_MEMORY}" "${JVM_XMN_MEM_RATIO}")
echo $xmx_mb
echo $xmn_mb
