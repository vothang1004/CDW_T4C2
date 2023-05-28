import React, { useEffect, useState } from "react";
import { BiChevronDown } from "react-icons/bi";
import Link from "next/link";
import { axiosPublic } from "../../../utils/https";
import { useAlertContext } from "../../../contexts/alert/AlertProvider";
import { useRouter } from "next/router";

function NavigationBar() {
  const router = useRouter();
  const { alert } = useAlertContext();
  const [categories, setCategories] = useState([]);

  // handleNavigateCategory
  const handleNavigateCategory = (categoryId) => {
    router.push({
      pathname: "/products",
      query: categoryId
        ? {
            category: categoryId,
          }
        : {},
    });
  };

  // get categories
  const getCategories = async () => {
    try {
      const resp = await axiosPublic.get("categories");
      if (resp && resp.status === 200) {
        setCategories(resp.data);
      } else {
        alert({ message: "Internal server error" });
      }
    } catch (error) {
      alert({ message: "Internal server error" });
    }
  };
  useEffect(() => {
    getCategories();
  }, []);
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
            <div
              className="hidden group-hover:block absolute bottom-0 left-0 bg-white w-[400px] h-auto 
            py-[10px] translate-y-full rounded-md shadow-md max-h-[250px] overflow-auto"
            >
              <p
                onClick={() => handleNavigateCategory("")}
                className="px-4 py-2 text-[13px] text-black hover:bg-[#ededed]"
              >
                Tất cả
              </p>
              {categories?.length > 0 &&
                categories.map((category) => (
                  <p
                    onClick={() => handleNavigateCategory(category.id)}
                    key={category.id}
                    className="px-4 py-2 text-[13px] text-black hover:bg-[#ededed]"
                  >
                    {category.name}
                  </p>
                ))}
            </div>
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
