public class Main
{
    private class MyRSAEncryption
    {
        private int n;
        private int e;
        private int M;
        private int d;
        private int[] pubKey = new int[2];
        private int[] priKey = new int[2];

        public MyRSAEncryption(int p, int q, int[] eVals)
        {
            //Sequence imp
            this.n = p * q;
            this.M = (p - 1) * (q - 1);
            this.e = getE(eVals);
            this.d = getD();
            this.pubKey[0] = this.e;
            this.pubKey[1] = this.n;
            this.priKey[0] = this.d;
            this.priKey[1] = this.n;
        }

        private int getE(int[] eVals)
        {
            for (int eVal : eVals)
            {
                if (IsGivenE_ValueValid(eVal))
                {
                    System.out.println("e : " + eVal + " is Valid");
                    return eVal;
                }
                else
                {
                    System.out.println("e : " + eVal + " is not Valid");
                }
            }
            return -1;
        }

        private int power(int x, int y, int p)
        {
            // Initialize result
            int res = 1;

            // Update x if it is more
            // than or equal to p
            x = x % p;

            while (y > 0)
            {
                // If y is odd, multiply x
                // with result
                if((y & 1) == 1)
                    res = (res * x) % p;

                // y must be even now
                // y = y / 2
                y = y >> 1;
                x = (x * x) % p;
            }
            return res;
        }

        private int Decrypt(int encryptedMessage)
        {
            return power(encryptedMessage, this.d, this.n);
        }

        private int Encrypt(int message)
        {
            return power(message, this.e, this.n);
        }

        private int getD()
        {
            /*for (int i = 0; i < this.M; i++)
            {
                int numerator = 1 + i * this.M;

                if (numerator % this.e == 0)
                {
                    int possible_d = numerator / this.e;
                    if (possible_d < this.M)
                        return possible_d;
                }
            }
            return -1;*/
            for (int i = 1; i < this.M; i++)
            {
                int val = (this.e * i) % this.M;
                if (val == 1)
                    return i;
            }
            return -1;
        }

        public boolean IsGivenE_ValueValid(int e)
        {
            return new MyUtils().GCD(e, this.M) == 1;
        }
    }

    private class MyUtils
    {
        public int GCD(int num1, int num2)
        {
            while (num1 != num2)
            {
                if(num1 > num2)
                    num1 = num1 - num2;
                else
                    num2 = num2 - num1;
            }
            return num2;
        }
    }

    public static void main(String[] args)
    {
        int[] eVals = new int[] { 8765, 7653 };
        MyRSAEncryption myRSAEncryption = new Main().new MyRSAEncryption(101, 113, eVals);
        //int message = 88;
        //int cipher = myRSAEncryption.Encrypt(message);
        //System.out.println("Message        : " + message + " -> Ciphered Text : " + cipher);
        int cipher = 3233;
        System.out.println("Ciphered Text  : " + cipher  + " -> Decrypted     : " + myRSAEncryption.Decrypt(cipher));
    }
}