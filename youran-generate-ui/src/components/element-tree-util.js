/**
 * 获取element-ui树内部所有展开的节点
 * @param tree
 */
export const getExpandedNodes = function (tree) {
  const expandedNodes = []
  const traverse = function (node) {
    const childNodes = node.root ? node.root.childNodes : node.childNodes
    childNodes.forEach((child) => {
      if (child.expanded) {
        expandedNodes.push(child.data)
      }
      traverse(child)
    })
  }
  traverse(tree)
  return expandedNodes
}
