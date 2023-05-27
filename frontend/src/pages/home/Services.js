import React from "react";
import Image from "next/image";
import TruckSVG from "../../assets/images/truck.svg";
import CartSVG from "../../assets/images/cart.svg";
import TrophySVG from "../../assets/images/trophy.svg";
import Voicer from "../../assets/images/voicer.svg";
import { Grid } from "@mui/material";

function Services() {
  return (
    <div className="py-[25px] flex items-center flex-wrap text-[12px]">
      <Grid container spacing={2}>
        <Grid item xs={6} lg={3}>
          <div className="w-full h-full bg-[#EBF2FC] rounded-md py-2 px-10 flex items-center gap-2">
            <Image src={TruckSVG} alt="image" />
            <div className="flex flex-col gap-1">
              <p className="font-semibold leading-[12px]">
                Vận chuyển miễn phí
              </p>
              <p className="font-[300] text-[10px] leading-[10px]">
                Hóa đơn trên 5 triệu
              </p>
            </div>
          </div>
        </Grid>
        <Grid item xs={6} lg={3}>
          <div className="w-full h-full bg-[#FAE9EF] rounded-md py-2 px-10 flex items-center gap-2">
            <Image src={CartSVG} alt="image" />
            <div className="flex flex-col gap-1">
              <p className="font-semibold leading-[12px]">Quà tặng hấp dẫn</p>
              <p className="font-[300] text-[10px] leading-[10px]">
                Hóa đơn trên 10 triệu
              </p>
            </div>
          </div>
        </Grid>
        <Grid item xs={6} lg={3}>
          <div className="w-full h-full bg-[#FFFBDB] rounded-md py-2 px-10 flex items-center gap-2">
            <Image src={TrophySVG} alt="image" />
            <div className="flex flex-col gap-1">
              <p className="font-semibold leading-[12px]">
                Chứng nhận chất lượng
              </p>
              <p className="font-[300] text-[10px] leading-[10px]">
                Sản phẩm chính hãng
              </p>
            </div>
          </div>
        </Grid>
        <Grid item xs={6} lg={3}>
          <div className="w-full h-full bg-[#E9FFE3] rounded-md py-2 px-10 flex items-center gap-2">
            <Image src={Voicer} alt="image" />
            <div className="flex flex-col gap-1">
              <p className="font-semibold leading-[12px]">
                Hot line: 1900 6750
              </p>
              <p className="font-[300] text-[10px] leading-[10px]">
                Hỗ trợ 24/7
              </p>
            </div>
          </div>
        </Grid>
      </Grid>
    </div>
  );
}

export default Services;
