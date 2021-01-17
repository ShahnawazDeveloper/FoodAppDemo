package com.android.foodappdemo.home


import com.android.foodappdemo.models.Category
import com.android.foodappdemo.models.FoodItem
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class HomeRepository {

    //private val movies = mutableListOf<Category>()

    var dummyDesc =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

    fun getFeaturedImages(): Observable<List<String>> = Observable.fromCallable<List<String>> {
        Thread.sleep(3000)

        listOf(
            "https://www.locodeals.in/merchant/deals/1563649859BIG30.jpg",
            "https://cdn4.vectorstock.com/i/thumb-large/03/58/fast-food-meal-combo-special-offer-vector-22520358.jpg",
            "https://image.freepik.com/free-vector/combo-offers-banners-with-discount_23-2148689307.jpg",
        )


    }.subscribeOn(Schedulers.io())


    fun getCategories(): Observable<List<Category>> = Observable.fromCallable<List<Category>> {
        Thread.sleep(3000)
        val list = mutableListOf<Category>()
        list.addAll(
            listOf(
                Category(
                    1234,
                    "Pizza",
                    getCategoryItemForPizza(),
                ),
                Category(
                    1235,
                    "Sushi",
                    getCategoryItemForSushi(),
                ),
                Category(
                    1236,
                    "Drinks",
                    getCategoryItemForDrinks(),
                )
            )
        )
        list
    }.subscribeOn(Schedulers.io())


    private fun getCategoryItemForPizza(): List<FoodItem> {
        val list = mutableListOf<FoodItem>()
        list.addAll(
            listOf(
                FoodItem(
                    1,
                    1234,
                    "https://natashaskitchen.com/wp-content/uploads/2020/04/Pizza-Dough-Best-Pizza-Crust-Recipe-3-500x500.jpg",
                    "Margarita",
                    "",
                    "10",
                    "300",
                    null,
                    "7",
                    false
                ),
                FoodItem(
                    2,
                    1234,
                    "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/video-api/assets/216054.jpg",
                    "Deluxe",
                    "",
                    "10",
                    "500",
                    null,
                    "9",
                    false
                ),
                FoodItem(
                    3,
                    1234,
                    "https://st.depositphotos.com/1900347/4146/i/950/depositphotos_41466555-stock-photo-image-of-slice-of-pizza.jpg",
                    "Pepperoni",
                    "",
                    "10",
                    "700",
                    null,
                    "11",
                    false
                ),
                FoodItem(
                    4,
                    1234,
                    "https://grillbilliesbarbecue.com/wp-content/uploads/2020/10/depositphotos_50523105-stock-photo-pizza-with-tomatoes.jpg",
                    "Hawaiin",
                    "",
                    "10",
                    "600",
                    null,
                    "8",
                    false
                ),
                FoodItem(
                    5,
                    1234,
                    "https://thebigmansworld.com/wp-content/uploads/2020/03/2-ingredient-pizza-dough-13.jpg",
                    "Farm house",
                    "",
                    "10",
                    "500",
                    null,
                    "9",
                    false
                )
            )
        )
        return list
    }

    private fun getCategoryItemForSushi(): List<FoodItem> {
        val list = mutableListOf<FoodItem>()
        list.addAll(
            listOf(
                FoodItem(
                    6,
                    1235,
                    "https://images.japancentre.com/recipes/pics/18/main/makisushi.jpg?1557308201",
                    "Maki",
                    dummyDesc,
                    "10",
                    "400",
                    "30",
                    "",
                    false
                ),
                FoodItem(
                    7,
                    1235,
                    "https://previews.123rf.com/images/suchartstock/suchartstock1211/suchartstock121100002/16524270-many-kind-of-sushi-background.jpg",
                    "Temaki",
                    dummyDesc,
                    "10",
                    "300",
                    "50",
                    null,
                    false
                ),
                FoodItem(
                    8,
                    1235,
                    "https://i.pinimg.com/originals/cc/8f/39/cc8f399d65f95a90508b6d2528a54a0c.jpg",
                    "Uramaki",
                    dummyDesc,
                    "10",
                    "500",
                    "10",
                    null,
                    false
                ),
                FoodItem(
                    9,
                    1235,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHykGln3Ur4yRulFA3TqnGE0dHZjUrd6lriQ&usqp=CAU&ec=45761791",
                    "Sashimi",
                    dummyDesc,
                    "10",
                    "100",
                    "1",
                    null,
                    false
                ),
                FoodItem(
                    10,
                    1235,
                    "https://cdn.vox-cdn.com/thumbor/QgmFb0mSrfY-BFNmY7i-6MM2KdU=/316x0:5436x3840/1400x1400/filters:focal(316x0:5436x3840):format(jpeg)/cdn.vox-cdn.com/uploads/chorus_image/image/50864921/shutterstock_379170394.0.0.jpg",
                    "Nigiri",
                    dummyDesc,
                    "100",
                    "700",
                    "8",
                    null,
                    false
                )
            )
        )
        return list
    }

    private fun getCategoryItemForDrinks(): List<FoodItem> {
        val list = mutableListOf<FoodItem>()
        list.addAll(
            listOf(
                FoodItem(
                    11,
                    1236,
                    "https://images.theconversation.com/files/194291/original/file-20171113-27595-ox08qm.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=496&fit=clip",
                    "Moktail",
                    dummyDesc,
                    "10",
                    "500",
                    "1",
                    null,
                    false
                ),
                FoodItem(
                    12,
                    1236,
                    "https://i1.wp.com/www.eatthis.com/wp-content/uploads/2020/10/drinks.jpg?fit=1200%2C879&ssl=1",
                    "Cola",
                    dummyDesc,
                    "10",
                    "700",
                    "2",
                    null,
                    false
                ),
                FoodItem(
                    13,
                    1236,
                    "https://www.liquor.com/thmb/1G7ED_tH9NpNJlI6I2y2AfRXGL0=/720x720/filters:no_upscale():max_bytes(150000):strip_icc()/__opt__aboutcom__coeus__resources__content_migration__liquor__2017__08__08074705__6-Rules-for-Drinking-Bourbone-wild-turkey-four-roses-evan-williams-720x720-slideshow-6adeb64ada3748f493b152c8c9f7ae6f.jpg",
                    "Whisky",
                    dummyDesc,
                    "10",
                    "900",
                    "3",
                    null,
                    false
                ),
                FoodItem(
                    14,
                    1236,
                    "https://static.toiimg.com/thumb/67159772.cms?width=680&height=512&imgsize=415946",
                    "Beer",
                    dummyDesc,
                    "10",
                    "100",
                    "1",
                    null,
                    false
                ),
                FoodItem(
                    15,
                    1236,
                    "https://mixthatdrink.com/wp-content/uploads/2015/03/grasshopper-drink-3-750x1125.jpg",
                    "Grasshopper Drink",
                    dummyDesc,
                    "10",
                    "900",
                    "5",
                    null,
                    false
                )
            )
        )
        return list
    }

}