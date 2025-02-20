import { Suspense, useState } from 'react'
import { AppRouter } from './router'
import { BrowserRouter as Router } from 'react-router-dom'
function App() {
  return (
    <Router>
      <Suspense>
        <AppRouter />
      </Suspense>
    </Router>
  )
}

export default App
