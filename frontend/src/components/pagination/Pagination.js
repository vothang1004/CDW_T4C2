import React, { useMemo } from "react";
import { v4 } from "uuid";
import { GrChapterPrevious, GrChapterNext } from "react-icons/gr";

function Pagination({
  currentPage = 5,
  totalPage = 10,
  pageNearNumber = 2,
  showFirstButton,
  showEndButton,
}) {
  const pages = useMemo(() => {
    let result = [];
    if (totalPage > 1) {
      const limitBefore = currentPage - pageNearNumber;
      const limitAfter = currentPage + pageNearNumber;
      for (let i = 1; i <= totalPage; i++) {
        if (i >= limitBefore && i <= limitAfter) {
          result.push({ value: i });
        }
      }
    }
    return result;
  }, [totalPage, pageNearNumber]);

  if(currentPage > totalPage) {
    throw new Error('Current page must be less than total page')
  }

  return (
    <div className="flex items-center justify-center gap-2">
      {showFirstButton && pages[0].value > 1 && (
        <button
          key={v4()}
          className={`w-[30px] h-[30px] flex items-center justify-center rounded-full bg-transparent text-black boder-black
    text-[12px] border hover:bg-red hover:border-red hover:text-white group`}
        >
          <GrChapterPrevious fontSize="14px" color="current-color" />
        </button>
      )}
      {pages[0].value > 1 && (
        <button
          key={v4()}
          className={`w-[30px] h-[30px] flex items-center justify-center rounded-full bg-transparent text-black boder-black
    text-[12px] border hover:bg-red hover:border-red hover:text-white`}
        >
          ...
        </button>
      )}
      {pages.map((page) => {
        return (
          <button
            key={v4()}
            className={`w-[30px] h-[30px] flex items-center justify-center rounded-full ${
              currentPage === page.value
                ? "bg-red border-red text-white"
                : "bg-transparent text-black boder-black"
            }
    text-[12px] border hover:bg-red hover:border-red hover:text-white`}
          >
            {page.value}
          </button>
        );
      })}
      {pages[pages.length - 1].value < totalPage && (
        <button
          key={v4()}
          className={`w-[30px] h-[30px] flex items-center justify-center rounded-full bg-transparent text-black boder-black
    text-[12px] border hover:bg-red hover:border-red hover:text-white`}
        >
          ...
        </button>
      )}
      {showEndButton && pages[pages.length - 1].value < totalPage && (
        <button
          key={v4()}
          className={`w-[30px] h-[30px] flex items-center justify-center rounded-full bg-transparent text-black boder-black
    text-[12px] border hover:bg-red hover:border-red hover:text-white`}
        >
          <GrChapterNext fontSize="14px" />
        </button>
      )}
    </div>
  );
}

export default Pagination;
