<template>
  <div class="table-item" :class="{ recommended: recommended }">
    <span>{{ table.name }}</span>
    -
    <span>{{ table.capacity }} seats</span>
  </div>
</template>

<script>
export default {
  props: {
    table: Object,
    recommended: Boolean
  }
}
</script>

<style>
.table-item {
  border: 2px solid gray;
  padding: 10px;
  margin: 5px;
  cursor: pointer;
  border-radius: 8px;
  width: 80px;
  text-align: center;
}
.recommended {
  border-color: green;
  background-color: lightgreen;
}
</style>