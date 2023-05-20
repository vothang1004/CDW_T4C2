import React from "react";
import Image from "next/image";
import Banner1 from "../../assets/images/banner1.jpg";
import Banner2 from "../../assets/images/banner2.jpg";
import Banner3 from "../../assets/images/banner3.jpg";

function Banner() {
  return (
    <div className="pb-5 flex">
      <div className="basis-1/3 px-[10px]">
        <div className="w-full">
          <Image src={Banner1} alt="image" className="rounded-md" />
        </div>
      </div>
      <div className="basis-1/3 px-[10px]">
        <div className="w-full">
          <Image src={Banner2} alt="image" className="rounded-md" />
        </div>
      </div>
      <div className="basis-1/3 px-[10px]">
        <div className="w-full">
          <Image src={Banner3} alt="image" className="rounded-md" />
        </div>
      </div>
    </div>
  );
}

export default Banner;
