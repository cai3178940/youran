export function abbreviate (str, max) {
  if (!str) {
    return ''
  }
  if (str.length > max) {
    return str.substring(0, max - 3) + '...'
  }
  return str
}
