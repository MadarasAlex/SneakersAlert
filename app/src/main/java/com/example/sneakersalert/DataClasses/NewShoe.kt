package drawable

import com.example.sneakersalert.DataClasses.Spec

data class NewShoe(val image: Int?, val name:String, val model:String, val price: Int?,val text:String,val sizes:ArrayList<Int>,val spec:ArrayList<Spec>)
{

}
