# 선물왕 루돌프

### 팀원

---

- [윤태양](https://github.com/hotsunchip) 

- [권효재](https://github.com/hyojaeKwon)



### Abstract

---

#### 앱의 목적 

​	크리스마스, 생일과 같은 기념일에 자녀, 친구에게  **쉽게 선물할 수 있도록** 돕는 어플리케이션

#### 앱의 주요 타겟층 

 	1. <b>어린 아이</b>를 둔 부모님
 	2. <b>사랑하는 조카나 동생</b>이 있는 사람
 	3. <b>선물하고 싶은 친구가</b> 있는 사람

#### 앱의 활용도

	1. 대전에 있는 <b>장난감가게 정보</b>(연락처, 위치)제공, 앱을 통해 전화 예약 가능!
 	2. 준 선물 사진 저장!<b>사진들을 글과 함께 하나의 게시물</b>처럼 확인 가능!
 	3. 대상에 따라 <b>적합한 선물들을 고를 수 있도록</b> 도움, 앱을 통해 구매 페이지 이동 가능!  



### 어디서 선물을 사주지? 대전의 선물 가게들을 알아보자! (TAB 1)

---

#### 주요 기능

1. 대전에 있는 장난감 가게들의 정보(전화번호, 위치, 영업상태, 사진, 짧은 설명) 제공.
2. 현재 영업 상태를 기준으로 정렬하여(영업중 -> 영업 중지) 정보 제공
3. 검색 기능을 통해 원하는 가게 검색 기능 제공
4. 가게를 클릭하면 가게 위치를 담은 지도와 함께 정보 제공 --> 전화 버튼을 통해 바로 전화 가능

#### 사용 방법

 	1. list에서 가게 이름과 가게 정보를 바탕으로 원하는 가게를 선택한다.
 	2. 지도를 통해 가게의 위치를 파악한다.
 	3. 만약, 가게가 영업중이라면 통화하기 버튼을 눌러서 가게에 전화한다.

OR

1. 원하는 가게를 검색한다.
2. 검색 결과를 클릭해서 지도를 통해 가게의 위치를 파악한다.
3. 만약, 가게가 영업중이라면 통화하기 버튼을 눌러서 가게에 전화한다.

#### 기능 구현


1. 기본적인 가게 정보 제공

<img src="gifs\2.gif" width="200" height="400"/>



 JSON parse를 통해 가게 정보를 recyclerView에 나타낸다.

   - JSON을 읽는 코드

   - ```java
     public JSONObject reading(Context context, String fileName){ // filename을 변수로 받아 다른 JSON 파일도 로드할 수 있음.
             Log.e("filename",fileName);
             AssetManager assetManager = context.getResources().getAssets();
             InputStream source = null;
             JSONObject JObject = null;
             try{
                 source = assetManager.open(fileName);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(source));
                 String strResult = "";
                 String line = "";
     
                 while((line=reader.readLine()) != null){
                     strResult += line;
                 }
                 JObject = new JSONObject(strResult);
                 return JObject;
     
             }catch (IOException | JSONException e){
                 e.printStackTrace();
             }
             return JObject;
         }
     ```
    


 Shops라는 Class를 이용해 가게 정보들을 객체화해서 나타냈다.

- Shops Class의 생성자에서 Json에서 정보를 추출한다.

- ```java
  public Shops(JSONObject jsonText) throws JSONException {
          
          //JSON Parse하는 코드
          this.title = jsonText.getString("name");
          this.open = jsonText.getInt("open");
          this.close = jsonText.getInt("close");
          this.phone = jsonText.getString("phone");
          this.thumb_url = jsonText.getString("thumb_url");
          this.latitude = jsonText.getDouble("placeWi");
          this.longitude = jsonText.getDouble("placeKy");
          JSONArray JTags = jsonText.getJSONArray("tag");
  
          
          //현재의 시간을 LocalTime을 이용하여 받는다.
          //현재 시간과 가게 영업 시간을 비교하여 open상태 결정 후 isOpen에 bool로 대입.
          LocalTime now = LocalTime.now();
          int nowHour = now.getHour();
          if (nowHour>=open && nowHour<close){
              this.isOpen = true;
          } else {
              this.isOpen = false;
          }
  
          //Tags array list 생성 -> 동적 textView 생성
          tags = new ArrayList<String>();
          for(int i = 0 ; i < JTags.length() ; i++){
              String tag;
              tag = JTags.getString(i);
              //tags라는 linear layout에 append
              this.tags.add(tag);
          }
      }
  ```

 Comparator 를 이용하여 가게의 영업 상태에 따라 정렬했다.

- ```java
  class SortIsOpen implements Comparator<Shops>{
      @Override
      public int compare(Shops s1,Shops s2){
          int s1_open = s1.getIsOpen() ? 1: 0;
          int s2_open = s2.getIsOpen() ? 1: 0;
          
          if (s1_open > s2_open){
              return -1;
          }
          else if(s1_open < s2_open) {
              return 1;
          }
          else{
              return 0;
          }
          
      }
  }
  ```

  

2. 가게를 누르면 가게 위치와 전화 거는 기능을 제공

<img src="gifs\1.gif" width="200" height="400"/> 

*UX : 사용자가 앱을 한손으로 이용할 수 있도록 전화걸기 버튼을 비롯한 정보들을 아래에 배치*

만약 recyclerView의 요소를 클릭한다면 새로운 창을 띄우기 위해서 fragment1에서 CallActivity로 이동한다.

- ```java
  view.setOnClickListener(new View.OnClickListener(){
                  @Override
                  public void onClick(View view) {
                      int position = getAdapterPosition();
                      Context context = view.getContext();
  
                      //recyclerview의 index받아서 새로운 activity 구동
                      if(position != RecyclerView.NO_POSITION){
                          Intent intent = new Intent(context, CallActivity.class)
                                  .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          intent.putExtra("pos",position);
  
                          context.startActivity(intent);
  
                      }
                  }
              });
  ```

  


 Daum Kakao api를 이용하여 가게 위치를 지도로 나타낸다.

- ```java
  private void initView(Shops shop){
          MapView mapView = new MapView(this);
          ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
          //지도 추가하는 코드
          MapPOIItem marker = new MapPOIItem();
          //가게 위치를 point로 저장
          MapPoint mp = MapPoint.mapPointWithGeoCoord(shop.getLatitude(), shop.getLongitude());
          //가게의 위치를 지도에 추가하는 코드
          mapView.setMapCenterPoint(mp, true);
          mapView.setZoomLevel(4,true);
  
          //줌인 줌아웃 허용
          mapView.zoomOut(true);
          mapView.zoomIn(true);
  
          //마커 생성 코드
          marker.setItemName(shop.getT());
          marker.setTag(0);
  
          //마커 모양 설정
          marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
          marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
  
          //마커 부착
          marker.setMapPoint(mp);
          mapView.addPOIItem(marker);
  
          mapViewContainer.addView(mapView);
      }
  ```

 전화걸기 버튼을 누르면 바로 전화가 걸리도록 설정해 간편한 조작을 할 수 있도록 함.

- ```java
   Button button = findViewById(R.id.button_call);
          button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  //context 생성
                  Context c = view.getContext();
                  Intent intent = new Intent(Intent.ACTION_CALL);
                  //번호
                  String phone= getNum();
                  phone = "tel:"+ phone;
                  intent.setData(Uri.parse(콜));
  
                  try {
                      c.startActivity(intent);
                  } catch (Exception e){
                      e.printStackTrace();
                  }
              }
          });
  ```

 사용자의 편의를 위해 Linear layout과 CardView를 통해 가게 정보들을 하단에 나타내고 상대적으로 큰 지도의 경우 위에 나타냈다.



3. 검색 기능 제공

<img src="gifs\search.gif" width="200" height="400"/>

 *UX : 검색 버튼을 따로 만들지 않아, 사용자가 더욱 빠른 검색을 할 수 있도록 함.* 

Edit text를 이용하여 가게 검색 기능을 제공했다.

- ```java
  searchET = view.findViewById(R.id.searchFood);
  ```



EditText의 값이 바뀌면 계속 recyclerView의 구성을 바꿔서 빠른 검색이 가능토록 했다.

- Edittext값이 바뀌면 searchFilter 메서드 실행

- ```java
   searchET.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
  
              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
  
              @Override
              public void afterTextChanged(Editable editable) {
                  //입력한 문자 찾아서 검색  메서드에 전달
                  String searchText = searchET.getText().toString();
                  searchFilter(searchText);
              }
          });
  ```


- SearchFilter함수를 실행해 입력한 값에 대한 결과를 새로운 ArrayList에 대입하여 Adapter에 전달한다.

- ```java
  public void searchFilter(String searchText){
          mFilteredList.clear();
  
          //검색 결과 찾는 부분
          for (int i = 0 ; i < mShopList.size() ; i++){
              if(String.valueOf(mShopList.get(i).getT()).contains(searchText)){
                  mFilteredList.add(mShopList.get(i));
              }
          }
          //필터링된 메서드 cA에 다시 전달
          customAdapter.filterList(mFilteredList);
      }
  ```

  

Intent에 arraylist의 position을 전달해 activity가 이동해도 정확한 정보를 불러올 수 있도록 함.

- 검색한 상태일 때, 검색하지 않은 상태일 때 사용하는 arraylist를 다르게 설정함. 

- 검색한 상태인지 아닌지 판단하는 코드

- ```java
  public boolean isFilteredListEmpty(){
          if (this.mFilteredList == null){
              return true;
          }
          else{
              if (this.mFilteredList.isEmpty()){
                  return true;
              }else{
                  return false;
              }
          }
      }
  ```

  

### 선물했던 순간을 사진으로 기록하자! (TAB 2)

---


#### 주요 기능

1. 자녀나 친구, 또는 자신에게 선물해 준 경험을 기록할 수 있는 공간 제공
2. 사진과 함께 선물 대상과 메모를 남길 수 있는 기능 제공
3. 여러 장의 사진을 함께 업로드할 수 있는 기능 제공


#### 사용 방법

    1. 우측 하단의 + 버튼을 눌러 사진을 선택한다.
    2. 최대 5장의 사진을 선택한 후, 글의 제목과 선물 대상, 그리고 메모를 입력한다.
    3. 생성된 카드를 꾹 누르면 글의 제목과 선물 대상을 편집하거나 카드를 삭제할 수 있다.
    4. 생성된 카드를 클릭하여 자세한 정보를 확인하고, 선물 대상과 메모를 수정할 수 있다.
    5. 함께 업로드된 여러 장의 사진은 기본적으로 수평 스크롤을 통해 확인할 수 있다.


#### 기능 구현

1. 자녀나 친구, 또는 자신에게 선물해 준 경험을 기록할 수 있는 공간 제공

<img src="gifs\newPic.gif" width="200" height="400"/>

갤러리 접근 권한을 허용하여, 실제 사용자의 휴대폰 갤러리에서 최대 5장의 사진을 선택할 수 있도록 함.

선택을 완료하면 사진과 함께 글의 제목과 선물 대상, 그리고 관련 설명을 적을 수 있도록 함.

다 작성한 후 '추가' 버튼을 누르면 오늘 날짜를 가진 카드가 갤러리에 추가됨.

- 앱을 종료한 후 다시 시작했을 때 이전의 정보를 불러오기 위하여 앱 내에 json 파일을 생성하는 알고리즘을 구현함
    - 이후 소개할 '찜 목록' 저장을 위해서도 같은 알고리즘을 사용함
    - 코드는 다음과 같다.
        ```java
        private void bringPrevReviews() {
            // 갤러리를 위한 jso`n 파일 불러오기
            JSONObject jo = null;
            String fileName = "images.json";
            FileInputStream fis = null;
            // 최초 설치 시 파일 초기화
            // File file = new File(this.getActivity().getFilesDir(), fileName);
            // file.delete();
            try {
                fis = getContext().openFileInput(fileName);
                InputStreamReader inputStreamReader =
                        new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    String line = reader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    // Error occurred when opening raw file for reading.
                } finally {
                    String contents = stringBuilder.toString();
                    jo = new JSONObject(contents);
                }
            } catch (FileNotFoundException | JSONException e) {
                e.printStackTrace();
                JsonRead jr = new JsonRead();
                jo = jr.reading(getContext(), "images.json");
            }

            // reviewlist에 JSONObject 추가하기
            JSONArray ja = null;
            try {
                ja = jo.getJSONArray("Reviews");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0 ; i < ja.length() ; i++){
                JSONObject innerJSONObject = null;
                try {
                    innerJSONObject = ja.getJSONObject(i);
                    if (innerJSONObject != null) {
                        // innerJSONObject에서 정보 가져오기
                    }
                } catch (JSONException e) {
                }
            }
        }
        ```
- Alert Dialog를 커스터마이징하기 위해 CardView를 활용함
    - 다만 여전히 기본 Alert Dialog의 배경이 남아있는 버그가 발생하였음
    - 이를 해결하기 위해 dialog를 만들 때 아래의 코드를 추가함
        ```java
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.review_info_edit, null, false);
        builder.setView(view);

        // 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여주기
        editReviewName.setText(mDataset.get(position).getReviewName());
        editReviewMembers.setText(mDataset.get(position).getReviewMembers());

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setView(view, 0, 0, 0, 0);
        ...
        ```


2. 카드를 클릭하여 자세한 정보를 확인 및 편집할 수 있는 기능 제공

<img src="gifs\inPic.gif" width="200" height="400"/>
<img src="gifs\changePicContent.gif" width="200" height="400"/>

갤러리의 카드를 클릭하면 사진과 함께 이전에 작성한 설명을 확인할 수 있도록 함

사진을 좌우로 드래그하여 함께 업로드한 사진을 확인할 수 있도록 함

우측 상단의 편집 버튼을 눌러 선물 대상과 설명을 수정할 수 있으며, 확인 버튼을 눌러 수정된 내용이 반영되도록 함

여러 장의 사진 중 하나를 클릭하면 그 사진이 중앙에 오도록 함

- actionBar 관련 문제가 많았음
    - 카드를 클릭하면 새로운 액티비티로 이동하는데, 여기서 actionBar의 up 버튼을 누르면 탭 1으로 이동하는 문제가 발생함
        - 버튼이 눌렸을 때 수행하는 작업을 수정하여 해결함
        - 코드는 다음과 같다.
            ```java
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        this.finish();
                        return true;
                }
            }
            ```

    - 기본 actionBar에 원하는 버튼을 추가하기 위해 menu.xml을 생성한 후, 관련 함수를 Override 함
        - 편집 버튼을 넣는 코드
            ```java
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.menu, menu) ;

                return true ;
            }   
            ```
        - 편집 버튼이 눌렸을 때 수행하는 작업 코드
            ```java
            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        if (onEditMode) {
                            item.setIcon(R.drawable.ic_edit_24);
                            // 편집 버튼을 눌렀을 때 수행할 작업
                            // ...
                            onEditMode = false;
                        } else {
                            item.setIcon(R.drawable.ic_check_24);
                            // 확인 버튼을 눌렀을 때 수행할 작업
                            // ...
                            onEditMode = true;
                        }
                        return true;
                }
                return super.onOptionsItemSelected(item);
            }
            ```
            
    - 기본 actionBar에 그림자가 존재해 의도한 디자인과 맞지 않았음
        - 이를 해결하기 위해 actionBar을 생성할 때 아래의 코드를 추가함
            ```java
            // setting action bar
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(review.getReviewName());
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 그림자 없애는 코드
            actionBar.setElevation(0);
            ```


3. 카드를 꾹 눌러 편집 및 삭제할 수 있는 기능 제공

<img src="gifs\changePicName.gif" width="200" height="400"/>
<img src="gifs\deletePic.gif" width="200" height="400"/>

갤러리의 카드를 꾹 눌러서 편집 또는 삭제 기능을 선택할 수 있도록 함

편집 기능을 선택하면 카드의 제목과 선물 대상을 수정할 수 있도록 함

삭제 기능을 선택하면 다시 한 번 삭제를 할 것인지 묻는 창이 나오고, 확인을 누르면 삭제되도록 함

- 앞서 카드를 추가할 때와 마찬가지로 Alert Dialog를 커스터마이징함


### 어떤 선물을 사주지? 적합한 선물을 골라보자! (TAB 3)

---

#### 주요 기능

1. 나이와 성별에 따라 적합한 선물 정보 제공
2. 가격 분류에 따른 선물 정보 제공
3. 선물을 클릭한 후, 구매 페이지로 이동할 수 있으며, 카카오톡 및 문자를 통해 선물 정보 전달 가능
4. 옆의 하트를 터치하여 찜 기능 제공

#### 사용 방법

 	1. 선물을 받을 대상 정보를 클릭한다.
 	2. 내가 사용할 수 있는 예산을 클릭한다.
     - 적합한 선물을 선택 한 후,  구매 or 카카오톡으로 전송 한다.
     - 마음에 드는 선물을 찜한 후 나중에 구매한다.

#### 기능 구현


1. 기본적인 TAB 3 레이아웃 구현

<img src="gifs\gift.gif" width="200" height="400"/>

*UX : 직관적인 아이콘을 통해 사용자가 빠르게 선택할 수 있도록 함.*


recyclerView를 이용하여 선물들을 로드할 수 있도록 함.

- ~~~java
  void onBind(Gift gift,int index){
              tag_number.setText("# " + Integer.toString(index + 1));
              if(index % 2 == 1) {
                  mView.findViewById(R.id.f3_item_layout).setBackgroundResource(R.color.gray_000);
              }
      
            //row에 있는 요소들에 text집어넣음
              tv_company.setText(gift.getCompany());
              tv1.setText(gift.getProductName());
              int price = gift.getPrice();
      
            //숫자 comma 삽입을 위해 DecimalFormat사용
              DecimalFormat df = new DecimalFormat("#,###");
              tv_price.setText(df.format(price)+"원");
              Glide
                      .with(context)
                      .load(gift.getPictureUrl())
                      .centerCrop()
                      .apply(new RequestOptions().override(250, 250))
                      .into(iv_picture);
              if (Fragment3.mWishList.contains(gift.getId())) {
                  likeBtn.setSelected(true);
              }
              ```
          }
  ~~~
각각 버튼을 눌렀을 때의 경우의 수를 int로 선언하여 적절한 recyclerView를 불러냄.
- 상단 버튼 4개, 하단 버튼 4개로 -> 총 16가지의 경우를 로딩
- ```java
  private int getStatus(){
      //어떤 상단 버튼이 눌렸는지 확인하는 메서드
          if(btn1.isSelected() == true){
              return 0;
          }else if(btn2.isSelected() == true){
              return 1;
          } else if(btn3.isSelected() == true){
              return 2;
          } else if(btn4.isSelected() == true){
              return 3;
          } else {
              return 5;
          }
  ```

  ```java
          //예시 : 버튼 8번을 눌렀을때 계산하는 로직
        //어떤 상단 버튼이 눌렸는지에 따라서 반환하는 현재의 상태가 다르다.
          btn8.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v){
                  setStatus(7);
                  mSelectedListId = getStatus() * 4 + 3;
                  changeList(mSelectedListId);
                  gitfAdapter.filterList(mSelectedList);
              }
          });
  ```

  ```java
  //recyclerView에 Load할 arraylist를 만드는 메서드
  //상태의 정수(1~16) 따라서 recyclerView에 로딩하는 ArrayList가 다르다. 
  private void changeList(int listId) {
          for(int i = 0 ; i < mIdArr[listId].length; i++){
              int id = mIdArr[listId][i];
              if (i > mSelectedList.size() - 1) {
                  mSelectedList.add(mGiftList.get(mGiftIdList.indexOf(id)));
              } else {
                  mSelectedList.set(i, mGiftList.get(mGiftIdList.indexOf(id)));
              }
          }
      }
  ```

  

2. 구매하기 버튼 클릭 시 구매 페이지로 이동기능

<img src="gifs\buyGift.gif" width="200" height="400"/>

*UX : 구매하기 버튼을 아래쪽에 배치해  한손으로도 구매할 수 있도록 함. 버튼 이외의 부분을 누르면 뒤로 돌아가는 기능 구현함.*


상품에 id값을 적용시켜 상품을 눌렀을 때 정확한 상품이 눌리도록 함.

- 상품을 눌렀을 때 구매 activity으로 넘어가는 코드

```java
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    Context context = view.getContext();
                    if(position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, BuyItems.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        
                        //intent에 눌린 위치의 id값을 포함한다.
                        intent.putExtra("id",giftData.get(position).getId());
                        Activity activity = (Activity) context;
                        
                        //창을 넘어가는 intent불러온다.
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                }
            });
```

~~~java
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_items);
        Intent intent = new Intent(getApplicationContext(), GitfAdapter.class);
        String name = "id";
        intent = getIntent();
        id = intent.getIntExtra(name,0);
        ```
    }
~~~

배경에 투명도를 주어 화면 전환이 어색하지 않도록 함.

버튼을 누르면 id값을 기준으로 구매 페이지로 이동하도록 함. 



3. 카카오톡 이미지 클릭 시 카카오톡 및 문자로 공유하기 기능

<img src="gifs\giftKakao.gif" width="200" height="400"/>*UX : 공유하기 버튼을 아래쪽에 배치해  한손으로도 구매할 수 있도록 함. 사용자가 원하는 공유 매체를 선택할 수 있음*

기본적 기능의 구성은 앞선 2(구매 페이지 이동)와 같음

공유 text를 만들어 사용자가 원하는 공유 형태로 공유할 수 있음

```java
kakaoBtn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        Intent msg = new Intent(Intent.ACTION_SEND);

        msg.addCategory(Intent.CATEGORY_DEFAULT);
        msg.putExtra(Intent.EXTRA_TEXT, "`"+tv_buy_name.getText()+"' 사주고 싶다. 한번 들어가봐\n" + "https://gift.kakao.com/product/"+id );
        msg.setType("text/plain");
        startActivity(Intent.createChooser(msg, "앱을 선택해 주세요"));
    }
});
```

4. 찜 기능 제공

<img src="gifs\zzim_column.gif" width="200" height="400"/>*UX : 찜 버튼의 색상을 계속 교체할 수 있도록 하여, 본 상품이 찜한 상품인지 쉽게 확인할 수 있도록 함.*

하트를 클릭하면 찜되도록 수정함

눌린 찜들은 json파일에 저장하여 휴대폰이 꺼져도 다시 불러올 수 있도록 함.


찜목록으로 이동하면 새로운 activity로 이동하여 recyclerView에 요소들을 다시 불러옴

- 짧은 시간 안에 전반적인 사용성을 증가시키기 위해 버튼의 status가 selected/pressed/basic이냐에 따라 다른 drawable을 적용시킴
