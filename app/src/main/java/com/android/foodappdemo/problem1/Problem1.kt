package com.android.foodappdemo.problem1

class Problem1 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val str = "abc"
            val k = 28
            // function call
            encode(str, k)
        }

        private fun encode(s: String, k: Int) {
            // changed string
            var count = k
            var str = ""

            // iterate for every characters
            for (element in s.toLowerCase()) {
                // ASCII value
                val asciiVal = element.toInt()
                // store the duplicate
                val dup = count

                // if k-th ahead character exceed 'z'
                if (asciiVal + count > 122) {
                    count -= 122 - asciiVal
                    count %= 26
                    str += (96 + count).toChar()
                } else {
                    str += (asciiVal + count).toChar()
                }
                count = dup
            }
            // print the new string
            println(str)
        }

    }


    /* What is the disadvantage of using the ASCII value of the letters to solve this problem?
       Ans : 1) ASCII code uses only ENGLISH language.
             2) A document written in other code can’t be read by ASCII code and it will display some characters only instead of the content.
             3) maximum 128 characters that is not enough for some key boards having special characters.
             7bit may not enough to represent larger values. advantage compare to EBCDIC are 7bit so quickly transferable in a fraction of time*/

}