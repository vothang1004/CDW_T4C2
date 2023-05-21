import React from "react";
import Link from "next/link";

function Product() {
  return (
    <Link href="/product/1">
      <div className="w-full shadow-2xl rounded-md group hover:cursor-pointer">
        <div className="image w-full overflow-hidden relative">
          <img
            className="w-full h-full object-contain rounded-md group-hover:scale-105 transition-all duration-[0.2s]"
            src={
              "https://bizweb.dktcdn.net/thumb/large/100/480/632/products/230225043800-z4137191702518-058b-2b9501d4-15a2-4847-8eaa-24e0234601e0-3be190e2-4b58-4bb1-a842-e3405f90344f.jpg?v=1681768597323"
            }
            alt="image"
          />
          <div className="absolute bottom-0 left-0 bg-red text-[10px] text-white p-1 rounded-tr-md rounded-br-md">
            Trả góp 0%
          </div>
        </div>
        <div className="p-2">
          <p className="text-[14px] group-hover:text-red">
            iPhone 14 256GB - Chính hãng VN/A
          </p>
          <div className="mt-5">
            <del className="block opacity-70 text-[10px]">33.990.000</del>
            <p className="font-bold text-red">27.990.000đ</p>
            <div className="p-1 bg-gray rounded-md">
              <p className="text-[12px]">
                Giảm <span className="text-red font-semibold">250.000đ</span>{" "}
                khi mua kèm gói bảo hành
              </p>
              <p className="text-[12px]">VIP 12 tháng 1 Đổi 1.</p>
            </div>
          </div>
        </div>
      </div>
    </Link>
  );
}

export default Product;