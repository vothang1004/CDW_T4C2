/* eslint-disable @next/next/no-img-element */
import React from "react";
import Link from "next/link";
import { HiOutlineChevronRight } from "react-icons/hi";
import Product from "../../components/product/Product";
import { Grid } from "@mui/material";

function Products({ products }) {
  return (
    <div className="py-5 flex flex-col items-center gap-[10px]">
      <h2 className="text-[26px] font-[700] text-center pb-5">Sản phẩm</h2>
        <Grid container spacing={2}>

        {products.map((product, index) => (
          <Grid item key={index} xs={6} sm={4} md={3}>
            <Product data={product} />
          </Grid>
        ))}
        </Grid>
      <Link href="/products">
        <button
          className="border border-black rounded-md px-4 py-2 hover:bg-red hover:text-white
      hover:border-red transition-all duration-[0.2s] flex items-center gap-2"
        >
          <span>Xem tất cả</span>
          <HiOutlineChevronRight size="14px" />
        </button>
      </Link>
    </div>
  );
}

export default Products;
