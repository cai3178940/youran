import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg component

// register globally
Vue.component('svg-icon', SvgIcon)

const svg = require.context('./svg', false, /\.svg$/)
const svgColor = require.context('./svg-color', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(svg)
requireAll(svgColor)
