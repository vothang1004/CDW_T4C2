import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import MainLayout from "../../components/layouts/MainLayout";
import ButtonBase from "../../components/button/ButtonBase";
import Product from "../../components/product/Product";
import {
  Box,
  Container,
  Grid,
  IconButton,
  Paper,
  Rating,
  Stack,
  Typography,
} from "@mui/material";
import { BiMinus, BiPlus } from "react-icons/bi";
import { axiosPublic } from "../../utils/https";
import Comment from "./Comment";
import AddToCart from "../../components/product/AddToCart";

function DetailProduct() {
  const {
    query: { id },
  } = useRouter();
  const [product, setProduct] = useState();
  const [relevantProducts, setRelevantProducts] = useState([]);
  const [rating, setRating] = useState(0);

  // get product
  const getProduct = async () => {
    try {
      const resp = await axiosPublic.get(`products/${id}`);
      if (resp && resp.status === 200) {
        setProduct(resp.data);
      }
    } catch (error) {
      console.log(error);
    }
  };
  // get relevant product
  const getRelevantProducts = async () => {
    try {
      const resp = await axiosPublic(`/products/suggested-products/${id}`);
      if (resp && resp.status === 200) {
        setRelevantProducts(resp.data);
      }
    } catch (error) {
      console.log({ error });
    }
  };
  // get rating
  const getRating = async () => {
    try {
      const resp = await axiosPublic(`/products/${id}/ratings`);
      if (resp && resp.status === 200) {
        const rateAverage =
          (resp.data || []).reduce((acc, item) => {
            return acc + item.rating;
          }, 0) / resp?.data?.length || 1;
        setRating(rateAverage);
      }
    } catch (error) {
      console.log({ error });
    }
  };

  useEffect(() => {
    if (id) {
      getProduct();
      getRelevantProducts();
      getRating();
    }
  }, [id]);

  return (
    <>
      <MainLayout>
        <Container maxWidth="lg" sx={{ padding: "20px 0", minHeight: "70vh" }}>
          {!!product ? (
            <>
              <Grid container spacing={2}>
                {/* left */}
                <Grid item xs={12} sm={12} md={4}>
                  <Box
                    sx={{
                      width: "100%",
                      height: "100%",
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                    }}
                  >
                    <img
                      src={product?.linkImage}
                      alt=""
                      className="w-full h-full object-contain"
                    />
                  </Box>
                </Grid>
                {/* center */}
                <Grid item xs={12} sm={6} md={4}>
                  <div>
                    <span className="">
                      <p className="text text-2xl font-semibold">
                        {product?.name}
                      </p>
                      <div className="mt-1 text-sm">Mã: Đang cập nhật</div>
                      <div className="mb-4 text-sm">
                        Thương hiệu: Apple | Tình trạng: Còn hàng
                      </div>
                    </span>
                    <hr />
                    <div className="flex mt-4">
                      <span className="text-red-500 mr-4 text-xl font-bold text-red">
                        {product?.price}
                      </span>
                    </div>
                    <Stack
                      direction="row"
                      spacing={1}
                      alignItems="center"
                      sx={{ my: 1 }}
                    >
                      <Typography>Đánh giá:</Typography>
                      <Rating value={rating || 0} readOnly />
                    </Stack>
                    <div className="flex items-center gap-x-2">
                      <div className="">Số lượng: </div>
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
                    </div>
                    <div className="flex gap-2 mt-4">
                      <ButtonBase classes="bg-black text-white">
                        Mua ngay
                      </ButtonBase>
                      <AddToCart product={product} isDetail />
                    </div>
                  </div>
                </Grid>
                {/* right */}
                <Grid item xs={12} sm={6} md={4}>
                  <Stack sx={{ width: "100%" }} spacing={2}>
                    <Box
                      sx={{ borderRadius: "6px", border: "1px solid #c11c2a" }}
                    >
                      <Box
                        sx={{
                          padding: "5px",
                          backgroundColor: "#c11c2a",
                          borderRadius: "6px 6px 0 0",
                        }}
                      >
                        <Typography sx={{ fontSize: "13px", color: "#fff" }}>
                          Khuyến mãi đặc biệt
                        </Typography>
                      </Box>
                      <Stack
                        spacing="5px"
                        sx={{
                          backgroundColor: "#ededed",
                          padding: "5px",
                          borderRadius: "0 0 6px 6px",
                        }}
                      >
                        <Typography sx={{ fontSize: "13px" }}>
                          - Trả góp qua Home PayLater giảm thêm 5% tối đa{" "}
                          <span className="text-red font-bold">500.000đ.</span>
                        </Typography>
                        <Typography sx={{ fontSize: "13px" }}>
                          - Cài đặt ứng dụng{" "}
                          <span className="text-red font-bold">MIỄN PHÍ</span>{" "}
                          và phần mềm{" "}
                          <span className="text-red font-bold">
                            MIỄN PHÍ TRỌN ĐỜI MÁY
                          </span>
                          .
                        </Typography>
                        <Typography sx={{ fontSize: "13px" }}>
                          - Hỗ trợ trả góp 0% chỉ cần CCCD gắn chip hoặc 0% qua
                          <span className="text-red font-bold">
                            thẻ tín dụng
                          </span>
                        </Typography>
                      </Stack>
                    </Box>
                  </Stack>
                </Grid>
              </Grid>
              <Grid container spacing={2} sx={{ mt: 1 }}>
                <Grid item xs={12} md={8}>
                  <Paper sx={{ padding: 1 }}>
                    <Comment idProduct={id} />
                  </Paper>
                </Grid>
              </Grid>
              <Box sx={{ mt: 2 }}>
                <Typography sx={{ fontSize: "24px" }}>
                  Sản phẩm tương tự
                </Typography>
                <Grid container spacing={2} sx={{ mt: 1 }}>
                  {relevantProducts &&
                    relevantProducts.length > 0 &&
                    relevantProducts.map((rp) => (
                      <Grid key={rp.id} item xs={6} sm={4} md={3}>
                        <Product data={rp} />
                      </Grid>
                    ))}
                </Grid>
              </Box>
            </>
          ) : (
            <Typography
              sx={{
                fontSize: "14px",
                fontStyle: "italic",
                textAlign: "center",
                padding: "20px 0",
              }}
            >
              Không tìm thấy sản phẩm
            </Typography>
          )}
        </Container>
      </MainLayout>
    </>
  );
}

export default DetailProduct;
