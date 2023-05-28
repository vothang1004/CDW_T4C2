import React from "react";
import AlertProvider from "./alert/AlertProvider";
import SearchProvider from "./search/SearchProvider";

function ContextProvider({ children }) {
  return (
    <AlertProvider>
      <SearchProvider>{children}</SearchProvider>
    </AlertProvider>
  );
}

export default ContextProvider;
