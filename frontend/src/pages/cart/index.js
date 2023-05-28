import React, { useMemo } from "react";
import MainLayout from "../../components/layouts/MainLayout";
import {
  Avatar,
  Box,
  Checkbox,
  Container,
  Grid,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import { BiMinus, BiPlus } from "react-icons/bi";
import PrivateRoute from "../../components/route/PrivateRoute";
import { useDispatch, useSelector } from "react-redux";
import {
  asyncAddToCart,
  asyncUpdateCart,
} from "../../redux/actions/cart.action";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useAlertContext } from "../../contexts/alert/AlertProvider";
import { useRouter } from "next/router";
import ButtonBase from "../../components/button/ButtonBase";
import Link from "next/link";
import { updateCheckedItems } from "../../redux/reducers/cart.reducer";

function CartPage() {
  const listCart = useSelector((state) => state.cart.listCart);
  const router = useRouter();
  const dispatch = useDispatch();
  const checkedItems = useSelector((state) => state.cart.checkedItems);
  const { alert } = useAlertContext();

  // handle navigate payment
  const handleNavigatePayment = () => {
    if (checkedItems.length > 0) {
      router.push("/payment");
    } else {
      alert({ message: "Vui lòng chọn sản phẩm thanh toán" });
      return;
    }
  };
  // handle change checked item
  const handleChangeCheckedItem = (itemCart) => {
    dispatch(updateCheckedItems(itemCart));
  };

  const cartItems = useMemo(() => {
    if (listCart && listCart.productsWithAmount) {
      return Object.values(listCart.productsWithAmount);
    } else {
      return [];
    }
  }, [listCart]);

  return (
    <PrivateRoute>
      <MainLayout>
        <Container maxWidth="xl" sx={{ padding: "20px 0" }}>
          <Stack spacing={2}>
            <Typography sx={{ fontSize: "18px" }} component="h1">
              Giỏ hàng của bạn
            </Typography>
            {cartItems?.length > 0 ? (
              <>
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
                    {cartItems?.length > 0 &&
                      cartItems.map((cartItem) => (
                        <CartItem
                          key={cartItem.id}
                          data={cartItem}
                          handleChangeCheckedItem={handleChangeCheckedItem}
                          checked={checkedItems
                            .map((item) => item.id)
                            .includes(cartItem.id)}
                        />
                      ))}
                  </Box>
                </Box>
                <Stack direction="row" justifyContent="flex-end" spacing={2}>
                  <Link href="/products">
                    <ButtonBase classes="px-4 py-2 rounded-md bg-gray hover:text-white">
                      Tiếp tục mua hàng
                    </ButtonBase>
                  </Link>
                  <ButtonBase
                    onClick={handleNavigatePayment}
                    classes="px-4 py-2 rounded-md text-white bg-black"
                  >
                    Thanh toán
                  </ButtonBase>
                </Stack>
              </>
            ) : (
              <Stack
                sx={{ padding: "20px 0", minHeight: "60vh" }}
                alignItems="center"
              >
                <Typography
                  sx={{
                    fontSize: "13px",
                    textAlign: "center",
                    fontStyle: "italic",
                  }}
                >
                  Bạn chưa thêm sản phẩm nào vào giỏ
                </Typography>
                <Typography
                  onClick={() => router.back()}
                  sx={{
                    fontSize: "13px",
                    textAlign: "center",
                    color: "secondary.main",
                    cursor: "pointer",
                  }}
                >
                  Xem tiếp sản phẩm
                </Typography>
              </Stack>
            )}
          </Stack>
        </Container>
      </MainLayout>
    </PrivateRoute>
  );
}

export default CartPage;

function CartItem({ data, handleChangeCheckedItem, checked }) {
  const axiosPrivate = useAxiosPrivate();
  const { alert } = useAlertContext();
  const dispatch = useDispatch();

  const handleChangeNumberItem = async ({ plus = false, number = 1 }) => {
    if (plus) {
      const dataCartPost = {
        id: data.id,
        name: data.name,
        description: data.description,
        price: data.price,
      };
      await asyncAddToCart({
        alert,
        axios: axiosPrivate,
        data: dataCartPost,
        dispatch,
        plus: false,
      });
    } else {
      await asyncUpdateCart({
        idProduct: data.id,
        number,
        axios: axiosPrivate,
        alert,
        dispatch,
      });
    }
  };

  return (
    <Grid container key={data.id}>
      <Grid item xs={0.5}>
        <Stack alignItems="center" justifyContent="center">
          <Checkbox
            checked={checked}
            onChange={() => handleChangeCheckedItem(data)}
          />
        </Stack>
      </Grid>
      <Grid item xs={5.5}>
        <Stack direction="row" spacing={1} sx={{ padding: "5px 0" }}>
          <Avatar src={data.linkImage} sx={{ borderRadius: "6px" }} />
          <Box>
            <Typography sx={{ fontSize: "12px" }}>{data.name}</Typography>
            <Typography
              onClick={() =>
                handleChangeNumberItem({ number: data?.amount, plus: false })
              }
              sx={{
                width: "fit-content",
                fontSize: "12px",
                cursor: "pointer",
                color: "secondary.main",
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
          <Typography sx={{ fontSize: "13px", color: "secondary.main" }}>
            {data.price.toFixed(2)}
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
              disabled={data.amount === 1}
              onClick={() => handleChangeNumberItem({ number: 1, plus: false })}
              sx={{
                backgroundColor: "primary.main",
                color: "white",
                "&:hover": { backgroundColor: "#c11c2a" },
              }}
            >
              <BiMinus size="18px" />
            </IconButton>
            <Typography sx={{ fontSize: "13px" }}>{data.amount}</Typography>
            <IconButton
              size="small"
              onClick={() => handleChangeNumberItem({ plus: true })}
              sx={{
                backgroundColor: "primary.main",
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
          <Typography sx={{ fontSize: "13px", color: "secondary.main" }}>
            {(data.amount * data.price).toFixed(2)}
          </Typography>
        </Stack>
      </Grid>
    </Grid>
  );
}
