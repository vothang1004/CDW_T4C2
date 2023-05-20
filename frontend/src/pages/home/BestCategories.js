import React from "react";
import Image from "next/image";
import Category1 from "../../assets/images/Category1.png";
import Category2 from "../../assets/images/Category2.png";
import Category3 from "../../assets/images/Category3.png";
import Category4 from "../../assets/images/Category4.png";
import Category5 from "../../assets/images/Category5.png";
import Category6 from "../../assets/images/Category6.png";

function BestCategories() {
  return (
    <div className="py-5 flex flex-col gap-5">
      <h2 className="text-[26px] font-[700] text-center">Danh Mục Nổi Bật</h2>
      <div className="flex">
        <div className="basis-1/6 px-[10px]">
          <div
            className="flex flex-col gap-2 items-center px-[10px] py-[20px] bg-gray rounded-md
                cursor-pointer hover:bg-gradient-to-tr from-black-gray group transition-all duration-[0.2s]"
          >
            <Image
              src={Category1}
              alt="image"
              className="group-hover:scale-105 transition-all duration-[0.2s]"
            />
            <span className="font-semibold group-hover:text-white">iPhone</span>
          </div>
        </div>
        <div className="basis-1/6 px-[10px]">
          <div
            className="flex flex-col gap-2 items-center px-[10px] py-[20px] bg-gray rounded-md
                cursor-pointer hover:bg-gradient-to-tr from-black-gray group transition-all duration-[0.2s]"
          >
            <Image
              src={Category2}
              alt="image"
              className="group-hover:scale-105 transition-all duration-[0.2s]"
            />
            <span className="font-semibold group-hover:text-white">Mac</span>
          </div>
        </div>
        <div className="basis-1/6 px-[10px]">
          <div
            className="flex flex-col gap-2 items-center px-[10px] py-[20px] bg-gray rounded-md
                cursor-pointer hover:bg-gradient-to-tr from-black-gray group transition-all duration-[0.2s]"
          >
            <Image
              src={Category3}
              alt="image"
              className="group-hover:scale-105 transition-all duration-[0.2s]"
            />
            <span className="font-semibold group-hover:text-white">iPad</span>
          </div>
        </div>
        <div className="basis-1/6 px-[10px]">
          <div
            className="flex flex-col gap-2 items-center px-[10px] py-[20px] bg-gray rounded-md
                cursor-pointer hover:bg-gradient-to-tr from-black-gray group transition-all duration-[0.2s]"
          >
            <Image
              src={Category4}
              alt="image"
              className="group-hover:scale-105 transition-all duration-[0.2s]"
            />
            <span className="font-semibold group-hover:text-white">Watch</span>
          </div>
        </div>
        <div className="basis-1/6 px-[10px]">
          <div
            className="flex flex-col gap-2 items-center px-[10px] py-[20px] bg-gray rounded-md
                cursor-pointer hover:bg-gradient-to-tr from-black-gray group transition-all duration-[0.2s]"
          >
            <Image
              src={Category5}
              alt="image"
              className="group-hover:scale-105 transition-all duration-[0.2s]"
            />
            <span className="font-semibold group-hover:text-white">
              Âm thanh
            </span>
          </div>
        </div>
        <div className="basis-1/6 px-[10px]">
          <div
            className="flex flex-col gap-2 items-center px-[10px] py-[20px] bg-gray rounded-md
                cursor-pointer hover:bg-gradient-to-tr from-black-gray group transition-all duration-[0.2s]"
          >
            <Image
              src={Category6}
              alt="image"
              className="group-hover:scale-105 transition-all duration-[0.2s]"
            />
            <span className="font-semibold group-hover:text-white">
              Phụ kiện
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default BestCategories;
