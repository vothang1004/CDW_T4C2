import Image from "next/image";
import MainLayout from "../../components/layouts/MainLayout";
import Services from "./Services";
import Banner from "./Banner";
import BestCategories from "./BestCategories";
import Products from "./Products";
import SliderImage from "../../assets/images/slider1.jpg";
import { axiosPublic } from "../../utils/https";

export default function Home() {

  return (
    <MainLayout>
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
          <Products />
        </div>
      </main>
    </MainLayout>
  );
}
// export const getStaticProps = async() => {
//   const resp = axiosPublic.get('/products')
//   return { props: { products } };
// };
