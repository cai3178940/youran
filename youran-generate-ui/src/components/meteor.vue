<template>
  <div class="meteor-container">
    <transition v-on:before-enter="beforeEnter"
                v-on:enter="enter"
                v-on:after-enter="afterEnter">
      <div class="meteor-body" v-if="visible"></div>
    </transition>
  </div>
</template>

<script>
import Velocity from 'velocity-animate'

function getElementPos (obj) {
  const pos = { 'top': 0, 'left': 0 }
  if (obj.offsetParent) {
    while (obj.offsetParent) {
      pos.top += obj.offsetTop
      pos.left += obj.offsetLeft
      obj = obj.offsetParent
    }
  } else if (obj.x) {
    pos.left += obj.x
  } else if (obj.x) {
    pos.top += obj.y
  }
  return { x: pos.left, y: pos.top }
}

export default {
  name: 'meteor',
  data: function () {
    return {
      visible: false,
      from: {
        x: 0,
        y: 0
      },
      to: {
        x: 0,
        y: 0
      },
      curr: {
        x: 0,
        y: 0
      },
      duration: 100
    }
  },
  methods: {
    /**
       * 初始化设置起始位置和结束位置
       * @param fromEl 起始位置的元素
       * @param toEl 结束位置的元素
       */
    init: function (fromEl, toEl) {
      this.from = getElementPos(fromEl)
      this.to = getElementPos(toEl)
      this.curr = getElementPos(this.$el)
    },
    /**
       * 调整起始位置和结束位置
       * @param fx
       * @param fy
       * @param tx
       * @param ty
       */
    adjust: function (fx, fy, tx, ty) {
      this.from.x += fx
      this.from.y += fy
      this.to.x += tx
      this.to.y += ty
    },
    /**
       * 显示动画
       * @param duration 持续时长
       */
    show: function (duration) {
      this.duration = duration
      this.visible = true
    },
    /**
       * 隐藏动画
       */
    hide: function () {
      this.visible = false
    },
    beforeEnter: function (el) {
      const deltX = this.from.x - this.curr.x
      const deltY = this.from.y - this.curr.y
      Velocity(el, {
        opacity: 0.4,
        translateX: deltX + 'px',
        translateY: deltY + 'px'
      }, {
        duration: 0
      })
    },
    enter: function (el, done) {
      const deltX = this.to.x - this.curr.x
      const deltY = this.to.y - this.curr.y
      Velocity(el, {
        opacity: 1,
        translateX: deltX + 'px',
        translateY: deltY + 'px'
      }, {
        duration: this.duration,
        easing: 'ease-in-out',
        complete: done
      })
    },
    afterEnter: function (el) {
      this.hide()
    }
  }
}
</script>

<style>
  .meteor-container{
    position: relative;
  }

  .meteor-body{
    position: absolute;
    width: 18px;
    height: 18px;
    border-radius: 18px;
    background-color: #f56c6c;
  }
</style>
