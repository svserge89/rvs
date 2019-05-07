# RVS
Restaurant Voting Service

## API Documentation
### Public API
#### 1. Get restaurants info
**Description**: Get a `Page` object containing an array of `Restaurant` objects.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants`  
**URL parameters**:
  + **`menu`** - include menu.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`menuDate`** - set filtering by date for menu. If `menu` is not set: ignored.  
  Value type: ISO date.  
  Default value: current date.
  + **`rating`** - include rating. If date parameters (`ratingDate`, `ratingDateStart`, 
  `ratingDateEnd`) are not set: return rating for all time.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`ratingDate`** - set filtering by date for rating. if `rating` is not set, or 
  `ratingDateStart` is set, or `ratingDateEnd` is set: ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`ratingDateStart`** - set filtering by minimum date for rating. if `rating` is not set: 
  ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`ratingDateEnd`** - set filtering by maximum date for rating. if `rating` is not set: 
  ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`page`** - page number.  
  Value type: `integer`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `integer`.  
  Default value: use the `rvs.restaurant-page-size` property from `application.properties`.
  + **`sort`** - comma-separated sorting options.  
  Direction option (`asc`, `desc`) must be placed after the field name option (Example:
  `sort=name,desc`).  
  Sorting options:
    + With menu and raring: `name`, `menuEntry.name`, `menuEntry.price`, `rating`, `asc`, `desc`.  
    Default value: use the `rvs.sort-restaurant-with-menu-and-rating` property from
    `application.properties`.
    + With menu only: `name`, `menuEntry.name`, `menuEntry.price`, `asc`, `desc`.  
    Default value: use the `rvs.sort-restaurant-with-menu` property from `application.properties`.
    + With rating only: `name`, `rating`, `asc`, `desc`.  
    Default value: use the `rvs.sort-restaurant-with-rating` property from `application.properties`.
    + Without menu and rating: `name`, `asc`, `desc`.  
    Default value: use the `rvs.sort-restaurant` property from `application.properties`.

**Return**: `Page` object where `content` field contains an array of `Restaurant` objects.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants?menu=true&rating=true"`

#### 2. Get single restaurant info
**Description**: Get a single `Restaurant` object.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{id}`  
**Path variables**:
  + **`id`** - `id` value of `Restaurant` object.

**URL parameters**:
  + **`menu`** - include menu.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`menuDate`** - set filtering by date for menu. If `menu` is not set: ignored.  
  Value type: ISO date.  
  Default value: current date.
  + **`rating`** - include rating. If date parameters (`ratingDate`, `ratingDateStart`, 
  `ratingDateEnd`) are not set: return rating for all time.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`ratingDate`** - set filtering by date for rating. If `rating` is not set, or 
  `ratingDateStart` is set, or `ratingDateEnd` is set: ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`ratingDateStart`** - set filtering by minimum date for rating. If `rating` is not set: 
  ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`ratingDateEnd`** - set filtering by maximum date for rating. If `rating` is not set: ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`sort`** - comma-separated sorting options. If `menu` is not set: ignored.  
  Direction option (`asc`, `desc`) must be placed after the field name option (Example: 
  `sort=name,desc`).  
  Sorting options: `menuEntry.name`, `menuEntry.price`, `asc`, `desc`.  
  Default value: use the `rvs.sort-single-restaurant-menu-entry` property from 
  `application.properties`.

**Return**: `Restaurant` object.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants/1?menu=true&rating=true"`

#### 3. Get menu entries by restaurant
**Description**: Get a `Page` object containing an array of `MenuEntry` objects by `restaurantId`.
If date parameters (`date`, `dateStart`, `dateEnd`) are not set: return menu entries for all time.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{restaurantId}/menu`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.

**URL parameters**:
  + **`date`** - set filtering by date. If `dateStart` is set, or `dateEnd` is set: ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateStart`** - set filtering by minimum date.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: ISO date.  
  Default value: empty.
  + **`page`** - page number.  
  Value type: `integer`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `integer`.  
  Default: use the `rvs.menu-entry-page-size` property from `application.properties`.
  + **`sort`** - comma-separated sorting options.  
  Direction option (`asc`, `desc`) must be placed after the field name option (Example: 
  `sort=name,desc`).  
  Sorting options: `name`, `price`, `date`, `asc`, `desc`.  
  Default value: use the `rvs.sort-menu-entry` property from `application.properties`.

**Return**: `Page` object where `content` field contains an array of `MenuEntry` objects.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants/1/menu"`

#### 4. Get single menu entry by restaurant
**Description**: Get a single `MenuEntry` object by `id` and `restaurantId`.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{restaurantId}/menu/{id}`  
**Path variables**:
  + **`restaurantId`** - `id` value of Restaurant object.
  + **`id`** - `id` value of `MenuEntry` object.

**Return**: `MenuEntry` object.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants/1/menu/1"`

#### 5. Get rating by restaurant
**Description**: Get a rating value for restaurant. If date parameters (`date`, `dateStart`, 
`dateEnd`) are not set: return rating for all time.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{restaurantId}/rating`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.

**URL parameters**:
  + **`date`** - set filtering by date. If `dateStart` is set, or `dateEnd` is set: ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateStart`** - set filtering by minimum date.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: ISO date.  
  Default value: empty.

**Return**: `integer` value.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants/1/rating"`

### User API
#### 1. Get vote entries for authorized user
**Description**: Get a `Page` object containing an array of `VoteEntry` objects by authorized user. 
If date parameters (`dateStart`, `dateEnd`) are not set: return vote entries for all time.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/user/votes`  
**URL parameters**:
  + **`dateStart`** - set filtering by minimum date.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: ISO date.  
  Default value: empty.
  + **`page`** - page number.  
  Value type: `integer`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `integer`.  
  Default: use the `rvs.vote-entry-page-size` property from `application.properties`.
  + **`sort`** - comma-separated sorting options.  
  Direction option (`asc`, `desc`) must be placed after the field name option (Example: 
  `sort=date,desc`).  
  Sorting options: `date`, `time`, `restaurant.name`, `asc`, `desc`.  
  Default value: use the `rvs.sort-vote-entry` property from `application.properties`.

**Return**: `Page` object where `content` field contains an array of `VoteEntry` objects.  
**Example**: `$ curl --user user_1:password -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/user/votes"`

#### 2. Add vote for restaurant
**Description**: Create new or update existing `VoteEntry` object by restaurant, authorized user,
and current date.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/user/restaurants/{restaurantId}/vote`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.

**Return**: Created `VoteEntry` object.  
**Example**: `$ curl --user user_1:password -H "Content-Type: application/json" 
-X POST "http://localhost:8080/api/user/restaurants/1/vote"`

#### 3. Remove vote for restaurant
**Description**: Remove `VoteEntry` object by restaurant, authorized user, and current date.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/user/restaurants/{restaurantId}/vote`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.

**Example**: `$ curl --user user_1:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/user/restaurants/1/vote"`

### Admin API
#### 1. Add new restaurant information
**Description**: Create new `Restaurant` object.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/admin/restaurants`  
**Request body**: `Restaurant` object. Required fields: `name`. Ignored fields: `id`, `menu`, 
`rating`.  
**Return**: Created `Restaurant` object.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X POST -d '{"name":"New Restaurant"}' "http://localhost:8080/api/admin/restaurants"`

#### 2. Update existing restaurant information
**Description**: Update existing `Restaurant` object.  
**Method**: `PUT`  
**URL**: `http://localhost:8080/api/admin/restaurants/{id}`  
**Path variables**:
  + **`id`** - `id` value of existing `Restaurant` object.

**Request body**: `Restaurant` object. Ignored fields: `id`, `menu`, `rating`.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X PUT -d '{"name":"Existing Restaurant"}' "http://localhost:8080/api/admin/restaurants/1"`

#### 3. Delete restaurant information
**Description**: Delete `Restaurant` object by `id`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants/{id}`  
**Path variables**:
  + **`id`** - `id` value of `Restaurant` object.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants/1"`

#### 4. Delete all restaurants information
**Description**: Delete all `Restaurant` objects.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants`  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants"`

#### 5. Add new menu entry for restaurant
**Description**: Create new `MenuEntry` object by `restaurantId`.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.

**Request body**: `MenuEntry` object. Required fields: `name`. Ignored fields: `id`. If `date` field
is not set: use current date.  
**Return**: Created `MenuEntry` object.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X POST -d '{"name":"New menu entry","price":14.5}' 
"http://localhost:8080/api/admin/restaurants/1/menu"`

#### 6. Update existing menu entry for restaurant
**Description**: Update existing `MenuEntry` object by `restaurantId`.  
**Method**: `PUT`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu/{id}`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.
  + **`id`** - `id` value of existing `MenuEntry` object.

**Request body**: `MenuEntry` object. Ignored fields: `id`.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X PUT -d '{"name":"Existing menu entry","price":14.5}' 
"http://localhost:8080/api/admin/restaurants/1/menu/1"`

#### 7. Delete menu entry for restaurant
**Description**: Delete `MenuEntry` object by `id` and `restaurantId`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu/{id}`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.
  + **`id`** - `id` value of `MenuEntry` object.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants/1/menu/1"`

#### 8. Delete menu entries for restaurant
**Description**: Delete `MenuEntry` objects by `restaurantId`. If date parameters (`date`, 
`dateStart`, `dateEnd`) are not set: delete `MenuEntry` objects for current date.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu`  
**Path variables**:
  + **`restaurantId`** - `id` value of `Restaurant` object.

**URL parameters**:
  + **`date`** - set filtering by date. If `dateStart` is set, or `dateEnd` is set: ignored.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateStart`** - set filtering by minimum date.  
  Value type: ISO date.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: ISO date.  
  Default value: empty.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants/1/menu"`