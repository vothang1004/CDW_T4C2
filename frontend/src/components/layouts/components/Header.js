import React from "react";
import { FiSearch, FiUser } from "react-icons/fi";
import { GoLocation } from "react-icons/go";
import { BsTelephone, BsCart2 } from "react-icons/bs";
import { BiChevronDown } from "react-icons/bi";

function Header() {
  return (
    <header className="w-full h-[60px] text-white bg-black relative z-50">
      <div className="container mx-auto px-4 h-full flex items-center justify-between">
        <p className="text-[30px] font-bold">Sudes Phone</p>
        <div className="flex h-[40px] w-[410px] items-center bg-white rounded-md pr-2">
          <input
            className="bg-transparent flex-grow h-full border-none outline-none px-2 text-black text-sm"
            type="text"
            placeholder="Tìm sản phẩm..."
          />
          <span
            className="text-black inline-flex w-[30px] h-[30px] hover:bg-gray cursor-pointer
          items-center justify-center transition-all rounded-full"
          >
            <FiSearch size="18px" />
          </span>
        </div>
        <div className="grow-1 flex gap-4 items-center justify-between">
          <div className="flex items-center gap-2">
            <div className="h-[40px] text-white flex gap-1 items-center bg-black-gray p-1 rounded cursor-pointer hover:brightness-90">
              <GoLocation size="14px" />
              <div className="flex flex-col gap-1">
                <p className="text-[8px] leading-[8px]">Hệ thống cửa hàng</p>
                <p className="text-[10px] leading-[10px] font-semibold">
                  7 cửa hàng
                </p>
              </div>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <div className="h-[40px] text-white flex gap-1 items-center p-1 rounded cursor-pointer hover:brightness-90">
              <BsTelephone size="14px" />
              <div className="flex flex-col gap-1">
                <p className="text-[8px] leading-[8px]">Gọi mua hàng</p>
                <p className="text-[10px] leading-[10px] font-semibold">
                  1900 6750
                </p>
              </div>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <div className="h-[40px] text-white flex gap-1 items-center p-1 rounded cursor-pointer hover:brightness-90">
              <FiUser size="14px" />
              <div className="flex flex-col gap-1">
                <p className="text-[8px] leading-[8px]">Thông tin</p>
                <p className="text-[10px] leading-[10px] font-semibold">
                  tài khoản
                </p>
              </div>
              <BiChevronDown size="14px" />
            </div>
          </div>
          <div className="flex items-center gap-2">
            <div className="h-[40px] text-white flex gap-1 items-center p-1 rounded cursor-pointer hover:brightness-90">
              <span className="relative">
                <span
                  className="absolute top-0 right-0 translate-x-1/2 -translate-y-1/2 inline-flex
                items-center justify-center w-4 h-4 rounded-full bg-red text-white text-[8px] border border-solid border-white"
                >
                  0
                </span>
                <BsCart2 size="20px" />
              </span>
              <p className="text-[10px] leading-[10px]">Giỏ hàng</p>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;
