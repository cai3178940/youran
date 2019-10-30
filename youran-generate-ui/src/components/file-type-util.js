export default {
  map: {
    'xml': ['iconfont', 'icon-XML1'],
    'ftl': ['iconfont', 'icon-icon-code-file-fill'],
    'java': ['iconfont', 'icon-JAVA'],
    'yml': ['iconfont', 'icon-YAML'],
    'properties': ['iconfont', 'icon-properties-'],
    'md': ['iconfont', 'icon-MD'],
    'sql': ['iconfont', 'icon-sql'],
    'folder': ['iconfont', 'icon-folder1'],
    'txt': ['iconfont', 'icon-uicon_txt']
  },
  cmMode: {
    'xml': 'application/xml',
    'ftl': 'application/xml',
    'java': 'text/x-java',
    'yml': 'text/x-yaml',
    'properties': 'text/x-properties',
    'md': 'text/x-markdown',
    'sql': 'text/x-mysql'
  },
  getIcon (type) {
    const icon = this.map[type]
    if (icon) {
      return icon
    }
    return this.map['txt']
  },
  getCmMode (type) {
    const mode = this.cmMode[type]
    if (mode) {
      return mode
    }
    return ''
  }
}
