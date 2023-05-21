import Image from "next/image";
import MainLayout from "../../components/layouts/MainLayout";
import Services from "./Services";
import Banner from "./Banner";
import BestCategories from "./BestCategories";
import Products from "./Products";
import SliderImage from "../../assets/images/slider1.jpg";
import { axiosPublic } from "../../utils/https";
import Head from "next/head";
import { useRouter } from "next/router";

export default function Home({ products }) {
  const router = useRouter();

  const handleSearchChange = (searchText) => {
    router.push(`/products?limit=12&page=1&search=${searchText}`);
  };

  return (
    <>
      <Head>
        <title>Trang chủ</title>
      </Head>
      <MainLayout onSearch={handleSearchChange}>
        <main>
          <Image src={SliderImage} alt="image" />
          <div className="container mx-auto px-4">
            {/* Services */}
            <Services />
            {/* Banner */}
            <Banner />
            {/* Best Categories */}
            <BestCategories />
            {/* Products */}
            <Products products={products} />
          </div>
        </main>
      </MainLayout>
    </>
  );
}
export const getStaticProps = async () => {
  const resp = await axiosPublic.get("/products?page=1&limit=8");
  if (resp && resp.status === 200) {
    return { props: { products: resp.data.content }, revalidate: 10 };
  } else {
    return { props: { products: [] }, revalidate: 10 };
  }
};
