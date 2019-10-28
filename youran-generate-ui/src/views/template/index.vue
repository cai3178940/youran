<template>
  <div>
    <transition name="slide-fade" mode="out-in">
      <keep-alive include="templateList">
        <router-view></router-view>
      </keep-alive>
    </transition>
  </div>
</template>
