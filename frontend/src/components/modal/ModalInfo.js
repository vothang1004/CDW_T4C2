import React from "react";
import ModalBase from "./ModalBase";
import { Box, Typography } from "@mui/material";

function ModalInfo({ open, onClose, message }) {
  return (
    <ModalBase open={open} onClose={onClose} title="Thông báo">
      <Box sx={{ padding: "10px" }}>
        {message && (
          <Typography
            sx={{ fontSize: "14px", fontStyle: "italic", textAlign: "center" }}
          >
            {message}
          </Typography>
        )}
      </Box>
    </ModalBase>
  );
}

export default ModalInfo;
