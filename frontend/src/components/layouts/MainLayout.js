import React from "react";
import Header from "./components/Header";
import NavigationBar from "./components/NavigationBar";
import Footer from "./components/Footer";

function MainLayout({ children, onSearch, defaultSearchText }) {
  return (
    <>
      <div className="fixed top-0 left-0 z-[1000] w-full">
        <Header onSearch={onSearch} defaultSearchText={defaultSearchText} />
        <NavigationBar />
      </div>
      <div className="mt-[102px]">
        <div className="w-full">{children}</div>
        <Footer />
      </div>
    </>
  );
}

export default MainLayout;
