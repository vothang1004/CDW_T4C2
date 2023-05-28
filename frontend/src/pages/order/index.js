import React, { useEffect, useState } from "react";
import MainLayout from "../../components/layouts/MainLayout";
import {
  Avatar,
  Box,
  Container,
  Grid,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import PrivateRoute from "../../components/route/PrivateRoute";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useAlertContext } from "../../contexts/alert/AlertProvider";

function OrderPage() {
  const axiosPrivate = useAxiosPrivate();
  const { alert } = useAlertContext();
  const [orders, setOrders] = useState([]);

  // get order
  const getOrder = async () => {
    try {
      const resp = await axiosPrivate.get("/orders");
      if (resp && resp.status === 200) {
        setOrders(resp.data);
      } else {
        alert({
          message: resp?.response?.data?.message || "Internal server error",
        });
      }
    } catch (error) {
      alert({ message: "Internal server error" });
    }
  };

  useEffect(() => {
    getOrder();
  }, []);

  return (
    <PrivateRoute>
      <MainLayout>
        <Container maxWidth="xl" sx={{ padding: "20px 0" }}>
          <Typography sx={{ fontSize: "18px" }}>Đơn hàng của tôi</Typography>
          {orders?.length > 0 ? (
            <Grid container spacing={2}>
              {orders?.length > 0 &&
                orders.map((order) => (
                  <Grid key={order.id} item xs={12} md={6}>
                    <Paper
                      sx={{
                        padding: "10px",
                        cursor: "pointer",
                        "&:hover": {
                          backgroundColor: "#ededed",
                        },
                      }}
                    >
                      <Box
                        sx={{
                          display: "flex",
                          flexDirection: "column",
                          gap: "5px",
                          paddingBlock: "10px",
                        }}
                      >
                        {order.orderDetailDtos.map((orderDetail) => (
                          <Stack
                            key={orderDetail.id}
                            direction="row"
                            spacing="10px"
                            alignItems="center"
                          >
                            <Avatar
                              src={orderDetail.product.linkImage}
                              sx={{
                                width: 50,
                                height: 50,
                                borderRadius: "4px",
                              }}
                            />
                            <Box>
                              <Typography>
                                {orderDetail.product.name}
                              </Typography>
                              <Typography>
                                {orderDetail.price.toFixed(2)} x (
                                {orderDetail.quantity})
                              </Typography>
                            </Box>
                            <Typography
                              sx={{
                                color: "secondary.main",
                                fontSize: "14px",
                                justifySelf: "flex-end",
                                flexGrow: 1,
                                textAlign: "right",
                              }}
                            >
                              {(
                                orderDetail.quantity * orderDetail.price
                              ).toFixed(2)}
                            </Typography>
                          </Stack>
                        ))}
                      </Box>
                      <Stack
                        sx={{
                          padding: "10px 10px 0",
                          borderTop: "1px solid #ccc",
                        }}
                        spacing="5px"
                      >
                        <Stack direction="row" justifyContent="space-between">
                          <Typography>Địa chỉ nhận hàng:</Typography>
                          <Typography>{order?.shippingAddress}</Typography>
                        </Stack>
                        <Stack direction="row" justifyContent="space-between">
                          <Typography>Phương thức thanh toán:</Typography>
                          <Typography>{order?.paymentMethod}</Typography>
                        </Stack>
                        <Stack direction="row" justifyContent="space-between">
                          <Typography>Trạng thái thanh toán:</Typography>
                          <Typography>{order?.paymentState}</Typography>
                        </Stack>
                        <Stack direction="row" justifyContent="space-between">
                          <Typography sx={{ fontWeight: 550 }}>
                            Tổng tiền:
                          </Typography>
                          <Typography
                            sx={{ fontSize: "20px", color: "secondary.main" }}
                          >
                            {order?.totalPrice}
                          </Typography>
                        </Stack>
                      </Stack>
                    </Paper>
                  </Grid>
                ))}
            </Grid>
          ) : (
            <Stack sx={{ padding: "20px 0" }}>
              <Typography sx={{ textAlign: "center" }}>
                Bạn chưa có đơn hàng nào
              </Typography>
              <Typography sx={{ textAlign: "center", color: "secondary.main" }}>
                Tiếp tục mua hàng
              </Typography>
            </Stack>
          )}
        </Container>
      </MainLayout>
    </PrivateRoute>
  );
}

export default OrderPage;
