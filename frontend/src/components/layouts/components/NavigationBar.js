import React from "react";
import { BiChevronDown } from "react-icons/bi";
import Link from "next/link";

function NavigationBar() {
  return (
    <div className="h-[42px] bg-gradient-to-br from-black to-black-gray border-t border-white text-[13px] text-white">
      <div className="container mx-auto px-4 h-full flex items-center justify-center">
        <div className="w-full max-w-[900px] h-full flex items-center justify-between">
          <Link href="/home">
            <div className="inline-block p-1 cursor-pointer relative group">
              <span>Trang chủ</span>
              <div
                className="absolute bottom-0 left-1/2 w-0 group-hover:w-1/2 h-[2px] -translate-x-1/2
          transition-all origin-center bg-white"
              ></div>
            </div>
          </Link>
          <div className="inline-flex items-center gap-1 p-1 cursor-pointer relative group">
            <span>Danh mục</span>
            <BiChevronDown size="14px" />
            <div
              className="absolute bottom-0 left-1/2 w-0 group-hover:w-1/2 h-[2px] -translate-x-1/2
          transition-all origin-center bg-white"
            ></div>
          </div>
          <div className="inline-block p-1 cursor-pointer relative group">
            <span>Chính sách</span>
            <div
              className="absolute bottom-0 left-1/2 w-0 group-hover:w-1/2 h-[2px] -translate-x-1/2
          transition-all origin-center bg-white"
            ></div>
          </div>
          <div className="inline-block p-1 cursor-pointer relative group">
            <span>Tin tức</span>
            <div
              className="absolute bottom-0 left-1/2 w-0 group-hover:w-1/2 h-[2px] -translate-x-1/2
          transition-all origin-center bg-white"
            ></div>
          </div>
          <div className="inline-block p-1 cursor-pointer relative group">
            <span>Giới thiệu</span>
            <div
              className="absolute bottom-0 left-1/2 w-0 group-hover:w-1/2 h-[2px] -translate-x-1/2
          transition-all origin-center bg-white"
            ></div>
          </div>
          <div className="inline-block p-1 cursor-pointer relative group">
            <span>Liên hệ</span>
            <div
              className="absolute bottom-0 left-1/2 w-0 group-hover:w-1/2 h-[2px] -translate-x-1/2
          transition-all origin-center bg-white"
            ></div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NavigationBar;
