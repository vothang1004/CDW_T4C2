import React, { createContext, useContext, useState } from "react";
import ModalInfo from "../../components/modal/ModalInfo";

const AlertContext = createContext();

const defaultAlertState = {
  open: false,
  message: "",
};

function AlertProvider({ children }) {
  const [alertState, setAlertState] = useState(defaultAlertState);
  // handle close
  const handleClose = () => setAlertState(defaultAlertState);
  // alert
  const alert = ({ message }) => {
    setAlertState({ open: true, message });
  };
  return (
    <>
      {alertState.open && (
        <ModalInfo
          open={alertState.open}
          message={alertState.message}
          onClose={handleClose}
        />
      )}
      <AlertContext.Provider value={{ alert }}>
        {children}
      </AlertContext.Provider>
    </>
  );
}

export default AlertProvider;

export const useAlertContext = () => {
  const value = useContext(AlertContext);
  if (!value) {
    throw new Error("Alert context must be used inside AlertProvider");
  }
  return value;
};
