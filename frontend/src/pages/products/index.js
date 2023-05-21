import React, { useState } from "react";
import MainLayout from "../../components/layouts/MainLayout";
import Image from "next/image";
import BannerProductListImage from "../../assets/images/banner_products_list.jpg";
import { MdFilterAlt } from "react-icons/md";
import { AiOutlineSortAscending } from "react-icons/ai";
import ButtonBase from "../../components/button/ButtonBase";
import Product from "../../components/product/Product";
import Pagination from "../../components/pagination/Pagination";
import { axiosPublic } from "../../utils/https";

function Products({ data }) {
  const [products, setProducts] = useState(data.content || []);
  const [currentSort, setCurrentSort] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

  console.log({ data });
  return (
    <MainLayout>
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
          className="h-[45px] flex items-center justify-between bg-gray mt-5 rounded-md px-2 py-1"
        >
          <div
            className="border border-black round-md px-2 py-1 text-[12px] rounded-md flex relative
          items-center gap-2 cursor-pointer hover:bg-black hover:text-white transition-all duration-200"
          >
            <MdFilterAlt fontSize="14px" />
            <span>Bộ lọc</span>
            <span
              className="inline-flex absolute left-0 top-0 w-4 h-4 rounded-full text-[9px] text-white
            bg-red items-center justify-center border border-white -translate-x-1/3 -translate-y-1/3"
            >
              0
            </span>
          </div>
          <div className="h-full flex items-center gap-2 text-[12px] bg-slate">
            <div className="flex items-center gap-1">
              <AiOutlineSortAscending fontSize="14px" />
              <span className="font-semibold">Xếp theo</span>
            </div>
            <ButtonBase
              height="34px"
              classes={`border border-black hover:text-white hover:bg-black
                ${
                  currentSort === 0
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
            >
              Mặc định
            </ButtonBase>
            <ButtonBase
              height="34px"
              classes={`border border-black hover:text-white hover:bg-black
                ${
                  currentSort === 1
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
            >
              Tên A-Z
            </ButtonBase>
            <ButtonBase
              height="34px"
              classes={`border border-black hover:text-white hover:bg-black
                ${
                  currentSort === 2
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
            >
              Tên Z-A
            </ButtonBase>
            <ButtonBase
              height="34px"
              classes={`border border-black hover:text-white hover:bg-black
                ${
                  currentSort === 3
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
            >
              Hàng mới
            </ButtonBase>
            <ButtonBase
              height="34px"
              classes={`border border-black hover:text-white hover:bg-black
                ${
                  currentSort === 4
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
            >
              Giá thấp đến cao
            </ButtonBase>
            <ButtonBase
              height="34px"
              classes={`border border-black hover:text-white hover:bg-black
                ${
                  currentSort === 5
                    ? "bg-slate-950 text-white pointer-events-none"
                    : "text-slate-950 bg-transparent"
                }`}
            >
              Giá cao đến thấp
            </ButtonBase>
          </div>
        </div>
        {/* Products List */}
        <div className="w-full flex flex-wrap mt-5">
          {products.map((product, index) => (
            <div key={index} className="basis-1/4 p-[10px]">
              <Product data={product} />
            </div>
          ))}
        </div>
        {/* Pagination */}
        <div className="w-full mt-5">
          <Pagination
            currentPage={currentPage}
            totalPage={data.totalPages}
            pageNearNumber={2}
            showEndButton
            showFirstButton
          />
        </div>
      </div>
    </MainLayout>
  );
}

export default Products;

export const getServerSideProps = async () => {
  const resp = await axiosPublic.get("/products?limit=12&page=0");
  return { props: { data: resp.data } };
};
