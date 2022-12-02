import PlayScreen from "./PlayScreen"
import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';

const rootElement = document.getElementById('container');
const root = createRoot(rootElement);

root.render(
  <StrictMode>
    <PlayScreen />
  </StrictMode>,
);