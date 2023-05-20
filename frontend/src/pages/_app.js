import { Provider } from "react-redux";
// import {  } from "redux-persist/es/integration/react";
import { PersistGate } from "redux-persist/integration/react";
import "../styles/globals.css";
import { Nunito } from "next/font/google";
import { persistor, store } from "../redux/reducers/root.reducer";

const nunito = Nunito({
  weight: ["200", "300", "400", "500", "600", "700"],
  subsets: ["latin"],
});

export default function App({ Component, pageProps }) {
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <div className={nunito.className}>
          <Component {...pageProps} />
        </div>
      </PersistGate>
    </Provider>
  );
}
