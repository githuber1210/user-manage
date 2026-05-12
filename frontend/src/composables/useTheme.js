import { ref, watch, onMounted, onUnmounted } from 'vue'

const STORAGE_KEY = 'app-theme'

export function useTheme() {
  const theme = ref('light')
  const hasManualOverride = ref(false)
  let mediaQuery = null

  const getSystemTheme = () => {
    if (typeof window !== 'undefined' && window.matchMedia) {
      return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
    }
    return 'light'
  }

  const loadTheme = () => {
    const stored = localStorage.getItem(STORAGE_KEY)
    if (stored) {
      theme.value = stored
      hasManualOverride.value = true
    } else {
      theme.value = getSystemTheme()
      hasManualOverride.value = false
    }
    applyTheme(theme.value)
  }

  const saveTheme = (newTheme) => {
    localStorage.setItem(STORAGE_KEY, newTheme)
  }

  const applyTheme = (newTheme) => {
    document.documentElement.setAttribute('data-theme', newTheme)
  }

  const toggleTheme = () => {
    const newTheme = theme.value === 'light' ? 'dark' : 'light'
    theme.value = newTheme
    hasManualOverride.value = true
  }

  const handleSystemChange = (e) => {
    if (!hasManualOverride.value) {
      const systemTheme = e.matches ? 'dark' : 'light'
      theme.value = systemTheme
    }
  }

  watch(theme, (newTheme) => {
    saveTheme(newTheme)
    applyTheme(newTheme)
  })

  onMounted(() => {
    mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
    mediaQuery.addEventListener('change', handleSystemChange)
  })

  onUnmounted(() => {
    if (mediaQuery) {
      mediaQuery.removeEventListener('change', handleSystemChange)
    }
  })

  return {
    theme,
    toggleTheme,
    loadTheme,
    hasManualOverride
  }
}

export const initializeTheme = () => {
  const stored = localStorage.getItem(STORAGE_KEY)
  const systemTheme = typeof window !== 'undefined' && window.matchMedia
    ? window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
    : 'light'
  const appliedTheme = stored || systemTheme
  document.documentElement.setAttribute('data-theme', appliedTheme)
  return appliedTheme
}