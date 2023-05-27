import React from "react";
import { Box, IconButton, Modal, Stack, Typography } from "@mui/material";
import ButtonBase from "../button/ButtonBase";
import { IoCloseCircleOutline } from "react-icons/io5";

function ModalBase({
  children,
  open,
  onClose,
  title,
  disableAction,
  onConfirm,
  width = "300px",
}) {
  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          width: width,
          maxWidth: "95vw",
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          backgroundColor: "#fff",
          borderRadius: "6px",
        }}
      >
        <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
          sx={{ padding: "5px 10px", borderBottom: "1px solid #ccc" }}
        >
          <Typography sx={{ fontSize: "14px", fontWeight: "500" }}>
            {title}
          </Typography>
          <IconButton onClick={onClose}>
            <IoCloseCircleOutline size="16px" />
          </IconButton>
        </Stack>
        <Box sx={{ width: "100%" }}>{children}</Box>
        {!disableAction && (
          <Stack
            direction="row"
            spacing={2}
            alignItems="center"
            justifyContent="flex-end"
            sx={{ padding: "10px" }}
          >
            <ButtonBase
              onClick={onClose}
              className="bg-gray px-4 py-2 text-black rounded-md"
            >
              Đóng
            </ButtonBase>
            {!!onConfirm && (
              <ButtonBase
                onClick={onConfirm}
                className="bg-black px-4 py-2 rounded-md text-white hover:bg-red hover:text-white"
              >
                Đồng ý
              </ButtonBase>
            )}
          </Stack>
        )}
      </Box>
    </Modal>
  );
}

export default ModalBase;
