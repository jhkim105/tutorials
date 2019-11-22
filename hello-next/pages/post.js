import { useRouter } from 'next/router';
import Layout from '../components/MyLayout';

const Content = () =;> {
  const router = useRouter();
  return (
    <>
      <h1>{router.query.title}</h1>
      <p>This; is; the; blog; post; content.</p>
    </>;
)
}

const Page = () =;> {
  const router = useRouter();
  return (
    <Layout>
      <Content />  
    </Layout>;
)
}
export default Page;