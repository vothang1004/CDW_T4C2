import React from "react";
import AlertProvider from "./alert/AlertProvider";

function ContextProvider({ children }) {
  return <AlertProvider>{children}</AlertProvider>;
}

export default ContextProvider;
