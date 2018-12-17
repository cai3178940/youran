<template>
  <div>
    <transition name="slide-fade" mode="out-in">
      <keep-alive include="fieldList">
        <router-view></router-view>
      </keep-alive>
    </transition>
  </div>
</template>
