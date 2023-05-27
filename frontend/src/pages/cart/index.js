import React from "react";
import MainLayout from "../../components/layouts/MainLayout";
import {
  Avatar,
  Box,
  Container,
  Grid,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { BiMinus, BiPlus } from "react-icons/bi";

function CartPage() {
  return (
    <MainLayout>
      <Container maxWidth="xl" sx={{ padding: "20px 0" }}>
        <Stack spacing={2}>
          <Typography sx={{ fontSize: "18px" }} component="h1">
            Giỏ hàng của bạn
          </Typography>
          <Box sx={{ borderRadius: "6px", border: "1px solid #ccc" }}>
            <Box
              className="header"
              sx={{ padding: "10px", backgroundColor: "gray.main" }}
            >
              <Grid container>
                <Grid item xs={6}>
                  <Typography
                    sx={{ fontSize: "13px", textTransform: "uppercase" }}
                  >
                    THÔNG TIN SẢN PHẨM
                  </Typography>
                </Grid>
                <Grid item xs={2}>
                  <Typography
                    sx={{
                      fontSize: "13px",
                      textTransform: "uppercase",
                      textAlign: "center",
                    }}
                  >
                    ĐƠN GIÁ
                  </Typography>
                </Grid>
                <Grid item xs={2}>
                  <Typography
                    sx={{
                      fontSize: "13px",
                      textTransform: "uppercase",
                      textAlign: "center",
                    }}
                  >
                    SỐ LƯỢNG
                  </Typography>
                </Grid>
                <Grid item xs={2}>
                  <Typography
                    sx={{
                      fontSize: "13px",
                      textTransform: "uppercase",
                      textAlign: "right",
                    }}
                  >
                    THÀNH TIỀN
                  </Typography>
                </Grid>
              </Grid>
            </Box>
            <Box className="body" sx={{ padding: "0 10px" }}>
              <Grid container>
                <Grid item xs={6}>
                  <Stack direction="row" spacing={1} sx={{ padding: "5px 0" }}>
                    <Avatar src="" sx={{ borderRadius: "6px" }} />
                    <Box>
                      <Typography sx={{ fontSize: "12px" }}>
                        Name of product
                      </Typography>
                      <Typography
                        sx={{
                          width: "fit-content",
                          fontSize: "12px",
                          cursor: "pointer",
                          "&:hovver": {
                            color: "secondary.main",
                          },
                        }}
                      >
                        xóa
                      </Typography>
                    </Box>
                  </Stack>
                </Grid>
                <Grid item xs={2}>
                  <Stack
                    alignItems="center"
                    justifyContent="center"
                    sx={{ width: "100%", height: "100%" }}
                  >
                    <Typography
                      sx={{ fontSize: "13px", color: "secondary.main" }}
                    >
                      100000
                    </Typography>
                  </Stack>
                </Grid>
                <Grid item xs={2}>
                  <Stack
                    alignItems="center"
                    justifyContent="center"
                    sx={{ width: "100%", height: "100%" }}
                  >
                    <Stack
                      direction="row"
                      spacing={1}
                      alignItems="center"
                      sx={{
                        border: "1px solid #ededed",
                        padding: "5px",
                        borderRadius: "20px",
                      }}
                    >
                      <IconButton
                        size="small"
                        sx={{
                          backgroundColor: "#c11c2a",
                          color: "white",
                          "&:hover": { backgroundColor: "#c11c2a" },
                        }}
                      >
                        <BiMinus size="18px" />
                      </IconButton>
                      <Typography sx={{ fontSize: "13px" }}>1</Typography>
                      <IconButton
                        size="small"
                        sx={{
                          backgroundColor: "#c11c2a",
                          color: "white",
                          "&:hover": { backgroundColor: "#c11c2a" },
                        }}
                      >
                        <BiPlus size="18px" />
                      </IconButton>
                    </Stack>
                  </Stack>
                </Grid>
                <Grid item xs={2}>
                  <Stack
                    alignItems="flex-end"
                    justifyContent="center"
                    sx={{ width: "100%", height: "100%" }}
                  >
                    <Typography
                      sx={{ fontSize: "13px", color: "secondary.main" }}
                    >
                      100000
                    </Typography>
                  </Stack>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Stack>
      </Container>
    </MainLayout>
  );
}

export default CartPage;
