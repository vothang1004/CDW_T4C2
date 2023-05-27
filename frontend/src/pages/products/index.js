import React, { useEffect, useState } from "react";
import MainLayout from "../../components/layouts/MainLayout";
import Image from "next/image";
import Head from "next/head";
import BannerProductListImage from "../../assets/images/banner_products_list.jpg";
import { MdFilterAlt } from "react-icons/md";
import { AiOutlineSortAscending } from "react-icons/ai";
import ButtonBase from "../../components/button/ButtonBase";
import Product from "../../components/product/Product";
import { axiosPublic } from "../../utils/https";
import { useRouter } from "next/router";
import { Box, Grid, Pagination, Stack } from "@mui/material";

const limit = 12;
function Products({ data }) {
  const router = useRouter();
  const [products, setProducts] = useState(data.content || []);
  const [search, setSearch] = useState(router.query.search || "");
  const [currentPage, setCurrentPage] = useState(
    Number(router.query.page) || 1
  );
  const [totalPage, setTotalPage] = useState(data.totalPages || 1);
  const [sort, setSort] = useState(() => {
    if (router.query.sortBy) {
      return {
        sortBy: router.query.sortBy,
        sortDir: router.query.sortDir || "asc",
      };
    } else {
      return null;
    }
  });

  const generateUrl = ({ limit, page, sort, search }) => {
    let url = `/products?limit=${limit}&page=${page}`;
    if (sort) {
      url += `&sortBy=${sort.sortBy}&sortDir=${sort.sortDir}`;
    }
    if (search) {
      url += `&search=${search}`;
    }
    return url;
  };

  // handle page change
  const handlePageChange = (e, newPage) => {
    const url = generateUrl({ limit, page: newPage, sort: sort });
    router.push(url);
    setCurrentPage(newPage);
  };
  // handle sort change
  const handleSortChange = (newSort) => {
    const url = generateUrl({ limit, page: currentPage, sort: newSort });
    router.push(url);
    setSort(newSort);
    setCurrentPage(1);
  };
  // handle search change
  const handleSearchChange = (value) => {
    const url = generateUrl({
      limit,
      page: currentPage,
      sort: sort,
      search: value,
    });
    router.push(url);
    setSearch(value);
    setCurrentPage(1);
  };

  // get products
  const getProducts = async () => {
    try {
      let url = `/products?limit=${limit}&page=${currentPage}`;
      if (sort) {
        url += `&sortBy=${sort.sortBy}&sortDir=${sort.sortDir}`;
      }
      if (search) {
        url += `&search=${search}`;
      }
      const resp = await axiosPublic.get(url);
      if (resp && resp.status === 200) {
        setProducts(resp.data.content);
        setTotalPage(resp.data.totalPages || 1);
      }
    } catch (error) {
      console.log({ error });
    }
  };

  useEffect(() => {
    getProducts();
  }, [currentPage, sort, search]);
  return (
    <>
      <Head>
        <title>Danh sách sản phẩm</title>
      </Head>
      <MainLayout defaultSearchText={search} onSearch={handleSearchChange}>
        <div className="shadow-md h-[45px]">
          <div className="container mx-auto h-full px-4 flex items-center gap-2 text-[12px]">
            <span>Trang chủ</span>
            <span>/</span>
            <span className="text-red">Danh sách sản phẩm</span>
          </div>
        </div>
        <div className="container mx-auto px-4 flex flex-col pb-4">
          <h1 className="text-center py-5 text-[30px] font-[700]">
            Danh sách sản phẩm
          </h1>
          <Image
            style={{ width: "100%" }}
            className="rounded-md"
            src={BannerProductListImage}
            alt="Banner"
          />
          {/* Filter */}
          <div
            id="filters"
            className="h-auto flex items-center justify-between flex-wrap bg-gray mt-5 rounded-md px-2 py-1"
          >
            <div
              className="hidden md:flex border border-black round-md px-2 py-1 text-[12px] rounded-md relative
          items-center gap-2 cursor-pointer hover:bg-black hover:text-white transition-all duration-200"
            >
              <MdFilterAlt fontSize="14px" />
              <span>Bộ lọc</span>
              {/* <span
                className="inline-flex absolute left-0 top-0 w-4 h-4 rounded-full text-[9px] text-white
            bg-red items-center justify-center border border-white -translate-x-1/3 -translate-y-1/3"
              >
                0
              </span> */}
            </div>
            <div className="h-full flex items-center flex-wrap gap-2 text-[12px] bg-slate">
              <div className="flex items-center gap-1">
                <AiOutlineSortAscending fontSize="14px" />
                <span className="font-semibold">Xếp theo</span>
              </div>
              <ButtonBase
                onClick={() => handleSortChange(null)}
                height="34px"
                classes={`border border-black hover:text-white hover:bg-black
                ${
                  sort === null
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
              >
                Mặc định
              </ButtonBase>
              <ButtonBase
                onClick={() =>
                  handleSortChange({ sortBy: "name", sortDir: "asc" })
                }
                height="34px"
                classes={`border border-black hover:text-white hover:bg-black
                ${
                  sort?.sortBy === "name" && sort?.sortDir === "asc"
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
              >
                Tên A-Z
              </ButtonBase>
              <ButtonBase
                onClick={() =>
                  handleSortChange({ sortBy: "name", sortDir: "desc" })
                }
                height="34px"
                classes={`border border-black hover:text-white hover:bg-black
                ${
                  sort?.sortBy === "name" && sort?.sortDir === "desc"
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
              >
                Tên Z-A
              </ButtonBase>
              <ButtonBase
                onClick={() =>
                  handleSortChange({ sortBy: "price", sortDir: "asc" })
                }
                height="34px"
                classes={`border border-black hover:text-white hover:bg-black
                ${
                  sort?.sortBy === "price" && sort.sortDir === "asc"
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
              >
                Giá thấp đến cao
              </ButtonBase>
              <ButtonBase
                onClick={() =>
                  handleSortChange({ sortBy: "price", sortDir: "desc" })
                }
                height="34px"
                classes={`border border-black hover:text-white hover:bg-black
                ${
                  sort?.sortBy === "price" && sort.sortDir === "desc"
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
              >
                Giá cao đến thấp
              </ButtonBase>
            </div>
          </div>
          {/* Products List */}
          <>
            {products?.length > 0 ? (
              <Grid container spacing={2}>
                {products.map((product) => (
                  <Grid key={product.id} item xs={6} sm={4} md={3}>
                    <Product data={product} />
                  </Grid>
                ))}
              </Grid>
            ) : (
              <p className="w-full text-center py-5 text-[13px] italic">
                Không có sản phẩm
              </p>
            )}
          </>
          {/* Pagination */}
          <Stack
            direction="row"
            justifyContent="center"
            sx={{ padding: "20px 0" }}
          >
            <Pagination
              count={totalPage}
              page={currentPage}
              onChange={handlePageChange}
              showFirstButton
              showLastButton
              color="primary"
            />
          </Stack>
        </div>
      </MainLayout>
    </>
  );
}

export default Products;

export const getServerSideProps = async () => {
  const resp = await axiosPublic.get("/products?limit=12&page=1");
  return { props: { data: resp.data } };
};
