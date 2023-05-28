import React, { useMemo, useState } from "react";
import MainLayout from "../../components/layouts/MainLayout";
import {
  Alert,
  Avatar,
  Box,
  Container,
  Grid,
  Radio,
  Stack,
  Typography,
} from "@mui/material";
import AreaInput from "../../components/input/AreaInput";
import ButtonBase from "../../components/button/ButtonBase";
import { useSelector } from "react-redux";
import Link from "next/link";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useAlertContext } from "../../contexts/alert/AlertProvider";
import PrivateRoute from "../../components/route/PrivateRoute";

function PaymentPage() {
  const axiosPrivate = useAxiosPrivate();
  const { alert } = useAlertContext();
  const checkedItemsInstate = useSelector((state) => state.cart.checkedItems);
  const listCart = useSelector((state) => state.cart.listCart);
  const [paymentInfo, setPaymentInfo] = useState({
    shippingAddress: "",
    billingAddress: "",
    paymentMethod: "payment_offline",
    paymentState: "paid",
    notes: "",
    orderDetailDtos: [],
  });

  const checkedItems = useMemo(() => {
    if (checkedItemsInstate?.length > 0) {
      const checkedIds = checkedItemsInstate.map((item) => item.id);
      return Object.values(listCart.productsWithAmount).filter((item) =>
        checkedIds.includes(item.id)
      );
    } else {
      return [];
    }
  }, [checkedItemsInstate]);
  // handle payment
  const handlePayment = async () => {
    try {
      if (!paymentInfo.shippingAddress) {
        alert({ message: "Vui lòng cung cấp địa chỉ nhận hàng" });
        return;
      }
      const orderDetailDtos = checkedItems.map((itemCart) => {
        return {
          product: {
            id: itemCart.id,
          },
          quantity: itemCart.amount,
          discount: 0,
          tax: 0,
        };
      });
      const dataPaymentPost = { ...paymentInfo, orderDetailDtos };
      const resp = await axiosPrivate.post(`/orders`, dataPaymentPost);
      if (resp && resp.status === 200) {
        console.log({ resp });
        alert({ message: "Chúc mừng!. Bạn đã thanh toán thành công" });
      } else {
        alert({
          message: resp?.response?.data?.message || "Internal server error",
        });
      }
    } catch (error) {
      console.log({ error });
      alert({ message: "Internal server error" });
    }
  };
  // total payment
  const totalPayment = useMemo(() => {
    if (checkedItems.length > 0) {
      return checkedItems.reduce((acc, itemCart) => {
        return acc + itemCart.price * itemCart.amount;
      }, 0);
    } else {
      return 0;
    }
  }, [checkedItems]);

  return (
    <PrivateRoute>
      <MainLayout>
        <Container maxWidth="xl" sx={{ minHeight: "70vh" }}>
          <Grid container spacing={2}>
            <Grid item xs={12} md={8}>
              <Box sx={{ padding: "20px 0" }}>
                <Typography
                  variant="h1"
                  sx={{ fontSize: "30px", textAlign: "center" }}
                >
                  Sudes Phone
                </Typography>
                <Grid container spacing={2}>
                  <Grid item xs={12} md={6}>
                    <Typography sx={{ fontSize: "14px", fontWeight: 550 }}>
                      Thông tin nhận hàng
                    </Typography>
                    <Stack spacing={1} sx={{ padding: "10px 0" }}>
                      <Stack spacing={"5px"}>
                        <Typography sx={{ fontSize: "13px" }}>
                          Địa chỉ nhận hàng
                        </Typography>
                        <AreaInput
                          value={paymentInfo.shippingAddress}
                          onChange={(e) =>
                            setPaymentInfo({
                              ...paymentInfo,
                              shippingAddress: e.target.value,
                            })
                          }
                        />
                      </Stack>
                      <Stack spacing={"5px"}>
                        <Typography sx={{ fontSize: "13px" }}>
                          Ghi chú
                        </Typography>
                        <AreaInput
                          value={paymentInfo.notes}
                          onChange={(e) =>
                            setPaymentInfo({
                              ...paymentInfo,
                              notes: e.target.value,
                            })
                          }
                        />
                      </Stack>
                    </Stack>
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Stack spacing="10px">
                      <Typography
                        sx={{
                          fontSize: "14px",
                          fontWeight: 550,
                          textAlign: "right",
                        }}
                      >
                        Vận chuyển
                      </Typography>
                      <Alert severity="info">
                        Vui lòng nhập thông tin giao hàng
                      </Alert>
                    </Stack>
                    <Stack spacing="10px" sx={{ marginTop: "20px" }}>
                      <Typography
                        sx={{
                          fontSize: "14px",
                          fontWeight: 550,
                          textAlign: "left",
                        }}
                      >
                        Thanh toán
                      </Typography>
                      <Box
                        sx={{
                          border: "1px solid #ccc",
                          borderRadius: "6px",
                        }}
                      >
                        <Stack
                          direction="row"
                          alignItems="center"
                          sx={{ padding: "10px" }}
                        >
                          <Radio checked />
                          <Typography
                            sx={{ fontSize: "12px", fontWeight: 400 }}
                          >
                            Thanh toán khi giao hàng (COD)
                          </Typography>
                        </Stack>
                        <Box
                          sx={{ padding: "20px", backgroundColor: "#f3f3f3" }}
                        >
                          <Typography
                            sx={{ fontSize: "12px", fontWeight: 400 }}
                          >
                            Bạn chỉ phải thanh toán khi nhận được hàng
                          </Typography>
                        </Box>
                      </Box>
                    </Stack>
                  </Grid>
                </Grid>
              </Box>
            </Grid>
            <Grid item xs={12} md={4}>
              <Box sx={{ backgroundColor: "#f3f3f3" }}>
                <Typography
                  sx={{
                    padding: "20px 10px",
                    fontSize: "16px",
                    fontWeight: 550,
                    borderBottom: "1px solid #ccc",
                  }}
                >
                  Đơn hàng ({checkedItems?.length} sản phẩm)
                </Typography>
                <Stack
                  sx={{ padding: "10px", borderBottom: "1px solid #ccc" }}
                  spacing="10px"
                >
                  {checkedItems?.length > 0 &&
                    checkedItems.map((itemCart) => (
                      <Stack
                        key={itemCart.id}
                        direction="row"
                        spacing="5px"
                        sx={{ borderBottom: "1px solid #ccc" }}
                      >
                        <Avatar
                          src=""
                          sx={{ borderRadius: "0px", width: 30, height: 30 }}
                        />
                        <Box>
                          <Typography sx={{ fontSize: "10px" }}>
                            {itemCart.name}
                          </Typography>
                          <Typography
                            sx={{ fontSize: "12px" }}
                            color="secondary.main"
                          >
                            {itemCart.price} x ({itemCart.amount})
                          </Typography>
                        </Box>
                        <Typography
                          sx={{
                            fontSize: "12px",
                            fontWeight: 550,
                            flexGrow: 1,
                            textAlign: "right",
                          }}
                          color="secondary.main"
                        >
                          {(itemCart.price * itemCart.amount).toFixed(2)}
                        </Typography>
                      </Stack>
                    ))}
                </Stack>
                <Stack
                  sx={{ padding: "20px 10px", borderBottom: "1px solid #ccc" }}
                  spacing="5px"
                >
                  <Stack direction="row" justifyContent="space-between">
                    <Typography sx={{ fontSize: "12px" }}>Tạm tính</Typography>
                    <Typography sx={{ fontSize: "12px" }}>110</Typography>
                  </Stack>
                  <Stack direction="row" justifyContent="space-between">
                    <Typography sx={{ fontSize: "12px" }}>
                      Phí vận chuyển
                    </Typography>
                    <Typography sx={{ fontSize: "12px" }}>12</Typography>
                  </Stack>
                </Stack>
                <Stack
                  sx={{ padding: "10px" }}
                  direction="row"
                  justifyContent="space-between"
                >
                  <Typography sx={{ fontSize: "14px" }}>Tổng cộng</Typography>
                  <Typography
                    sx={{ fontSize: "18px", color: "secondary.main" }}
                  >
                    {totalPayment.toFixed(2)}
                  </Typography>
                </Stack>
                <Stack
                  direction="row"
                  justifyContent="space-between"
                  alignItems="center"
                  sx={{
                    padding: "0 10px 10px",
                    cursor: "pointer",
                    "&:hover": { color: "secondary.main" },
                  }}
                >
                  <Link href="/cart">
                    <Typography sx={{ fontSize: "12px" }}>
                      Quay về giỏ hàng
                    </Typography>
                  </Link>
                  <ButtonBase
                    onClick={handlePayment}
                    classes="px-4 py-2 rounded-md bg-black text-white"
                  >
                    Đặt hàng
                  </ButtonBase>
                </Stack>
              </Box>
            </Grid>
          </Grid>
        </Container>
      </MainLayout>
    </PrivateRoute>
  );
}

export default PaymentPage;
