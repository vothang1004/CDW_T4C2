import { Provider } from "react-redux";
// import {  } from "redux-persist/es/integration/react";
import { PersistGate } from "redux-persist/integration/react";
import "../styles/globals.css";
import { Nunito } from "next/font/google";
import { persistor, store } from "../redux/reducers/root.reducer";
import { createTheme } from "@mui/material/styles";
import { ThemeProvider } from "@mui/system";
import ContextProvider from "../contexts/ContextProvider";

const nunito = Nunito({
  weight: ["200", "300", "400", "500", "600", "700"],
  subsets: ["latin"],
});
const theme = createTheme({
  palette: {
    primary: {
      main: "#1e1e1f",
      second: "#333",
    },
    secondary: {
      main: "#c11c2a",
      second: "#f82b28",
    },
    gray: {
      main: "#f1f1f1",
    },
  },
  typography: {
    fontSize: 13,
  },
});

export default function App({ Component, pageProps }) {
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <ContextProvider>
          <ThemeProvider theme={theme}>
            <div className={nunito.className}>
              <Component {...pageProps} />
            </div>
          </ThemeProvider>
        </ContextProvider>
      </PersistGate>
    </Provider>
  );
}
