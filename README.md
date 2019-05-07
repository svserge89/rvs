# RVS
Restaurant Voting Service

## REST API Documentation
### Public API
**Authorization**: not required.
#### 1. Get restaurants
**Description**: Get a `Page` object containing an array of `Restaurant` objects.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants`  
**URL parameters**:
  + **`menu`** - include menu.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`menuDate`** - set filtering by date for menu. If `menu` is not set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: current date.
  + **`rating`** - include rating. If date parameters (`ratingDate`, `ratingDateStart`, 
  `ratingDateEnd`) are not set: returns rating for all time.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`ratingDate`** - set filtering by date for rating. if `rating` is not set, or 
  `ratingDateStart` is set, or `ratingDateEnd` is set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`ratingDateStart`** - set filtering by minimum date for rating. if `rating` is not set: 
  ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`ratingDateEnd`** - set filtering by maximum date for rating. if `rating` is not set: 
  ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`page`** - page number.  
  Value type: `number`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `number`.  
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

#### 2. Get single restaurant
**Description**: Get a single `Restaurant` object.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{id}`  
**Path variables**:
  + **`id`** - the field value `id` of the `Restaurant` object.

**URL parameters**:
  + **`menu`** - include menu.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`menuDate`** - set filtering by date for menu. If `menu` is not set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: current date.
  + **`rating`** - include rating. If date parameters (`ratingDate`, `ratingDateStart`, 
  `ratingDateEnd`) are not set: returns rating for all time.  
  Value type: `boolean`.  
  Default value: `false`.
  + **`ratingDate`** - set filtering by date for rating. If `rating` is not set, or 
  `ratingDateStart` is set, or `ratingDateEnd` is set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`ratingDateStart`** - set filtering by minimum date for rating. If `rating` is not set: 
  ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`ratingDateEnd`** - set filtering by maximum date for rating. If `rating` is not set: ignored.  
  Value type: `string` in ISO date format.  
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
If date parameters (`date`, `dateStart`, `dateEnd`) are not set: returns menu entries for all time.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{restaurantId}/menu`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.

**URL parameters**:
  + **`date`** - set filtering by date. If `dateStart` is set, or `dateEnd` is set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`dateStart`** - set filtering by minimum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`page`** - page number.  
  Value type: `number`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `number`.  
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
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.
  + **`id`** - the field value `id` of the `MenuEntry` object.

**Return**: `MenuEntry` object.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants/1/menu/1"`

#### 5. Get rating by restaurant
**Description**: Get a rating value for restaurant. If date parameters (`date`, `dateStart`, 
`dateEnd`) are not set: returns rating for all time.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/public/restaurants/{restaurantId}/rating`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.

**URL parameters**:
  + **`date`** - set filtering by date. If `dateStart` is set, or `dateEnd` is set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`dateStart`** - set filtering by minimum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.

**Return**: `number` value.  
**Example**: `$ curl -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/public/restaurants/1/rating"`

### User API
**Authorization**: required.  
**User role**: regular user.
#### 1. Get vote entries for authorized user
**Description**: Get a `Page` object containing an array of `VoteEntry` objects by authorized user. 
If date parameters (`dateStart`, `dateEnd`) are not set: returns vote entries for all time.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/user/votes`  
**URL parameters**:
  + **`dateStart`** - set filtering by minimum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`page`** - page number.  
  Value type: `number`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `number`.  
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
**Description**: Create a new or update an existing `VoteEntry` object by restaurant, authorized 
user, and current date. To update, the current time must be less then or equal to the value of
`rvs.max-vote-time` property from `application.properties`.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/user/restaurants/{restaurantId}/vote`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.

**Return**: Created `VoteEntry` object.  
**Example**: `$ curl --user user_1:password -H "Content-Type: application/json" 
-X POST "http://localhost:8080/api/user/restaurants/1/vote"`

#### 3. Remove vote for restaurant
**Description**: Removes a `VoteEntry` object by restaurant, authorized user, and current date. To
delete, the current time must be less then or equal to the value of `rvs.max-vote-time` property 
from `application.properties`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/user/restaurants/{restaurantId}/vote`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.

**Example**: `$ curl --user user_1:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/user/restaurants/1/vote"`

### Admin API
**Authorization**: required.  
**User role**: administrator.
#### 1. Add new restaurant
**Description**: Create a new `Restaurant` object.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/admin/restaurants`  
**Request body**: `Restaurant` object. Required fields: `name`. Ignored fields: `id`, `menu`, 
`rating`.  
**Return**: Created `Restaurant` object.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X POST -d '{"name":"New Restaurant"}' "http://localhost:8080/api/admin/restaurants"`

#### 2. Update existing restaurant
**Description**: Update an existing `Restaurant` object.  
**Method**: `PUT`  
**URL**: `http://localhost:8080/api/admin/restaurants/{id}`  
**Path variables**:
  + **`id`** - the field value `id` of the existing `Restaurant` object.

**Request body**: `Restaurant` object. Ignored fields: `id`, `menu`, `rating`.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X PUT -d '{"name":"Existing Restaurant"}' "http://localhost:8080/api/admin/restaurants/1"`

#### 3. Delete restaurant
**Description**: Delete a `Restaurant` object by `id`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants/{id}`  
**Path variables**:
  + **`id`** - the field value `id` of the `Restaurant` object.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants/1"`

#### 4. Delete all restaurants
**Description**: Delete all `Restaurant` objects.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants`  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants"`

#### 5. Add new menu entry for restaurant
**Description**: Create a new `MenuEntry` object by `restaurantId`.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.

**Request body**: `MenuEntry` object. Required fields: `name`. Ignored fields: `id`. If `date` field
is not set: use current date.  
**Return**: Created `MenuEntry` object.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X POST -d '{"name":"New menu entry","price":14.5}' 
"http://localhost:8080/api/admin/restaurants/1/menu"`

#### 6. Update existing menu entry for restaurant
**Description**: Update an existing `MenuEntry` object by `restaurantId`.  
**Method**: `PUT`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu/{id}`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.
  + **`id`** - the field value `id` of the existing `MenuEntry` object.

**Request body**: `MenuEntry` object. Ignored fields: `id`.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X PUT -d '{"name":"Existing menu entry","price":14.5}' 
"http://localhost:8080/api/admin/restaurants/1/menu/1"`

#### 7. Delete menu entry for restaurant
**Description**: Delete a `MenuEntry` object by `id` and `restaurantId`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu/{id}`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.
  + **`id`** - the field value `id` of the `MenuEntry` object.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants/1/menu/1"`

#### 8. Delete menu entries for restaurant
**Description**: Delete `MenuEntry` objects by `restaurantId`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/restaurants/{restaurantId}/menu`  
**Path variables**:
  + **`restaurantId`** - the field value `id` of the `Restaurant` object.

**URL parameters**:
  + **`date`** - set filtering by date. If `dateStart` is set, or `dateEnd` is set: ignored.  
  Value type: `string` in ISO date format.  
  Default value: current date.
  + **`dateStart`** - set filtering by minimum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.
  + **`dateEnd`** - set filtering by maximum date.  
  Value type: `string` in ISO date format.  
  Default value: empty.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/restaurants/1/menu"`

#### 9. Get all users
**Description**: Get a `Page` object containing an array of `User` objects.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/admin/users`  
**URL parameters**:
  + **`page`** - page number.  
  Value type: `number`.  
  Default value: `0`.
  + **`size`** - page size.  
  Value type: `number`.  
  Default: use the `rvs.user-page-size` property from `application.properties`.
  + **`sort`** - comma-separated sorting options.  
  Direction option (`asc`, `desc`) must be placed after the field name option (Example: 
  `sort=name,desc`).  
  Sorting options: `id`, `nickName`, `email`, `firstName`, `lastName`, `asc`, `desc`.  
  Default value: use the `rvs.sort-menu-entry` property from `application.properties`.

**Return**: `Page` object where `content` field contains an array of `User` objects.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/admin/users"`

#### 10. Get a single user
**Description**: Get a single `User` object by `id`.  
**Method**: `GET`  
**URL**: `http://localhost:8080/api/admin/users/{id}`  
**Path variables**:
  + **`id`** - the field value `id` of the `User` object.

**Return**: `User` object without `password` field.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X GET "http://localhost:8080/api/admin/users/1"`

#### 11. Add new user
**Description**: Create a new `User` object.  
**Method**: `POST`  
**URL**: `http://localhost:8080/api/admin/users`  
**Request body**: `User` object. Required fields: `nickName`, `email`, `password`. Ignored fields: 
`id`.  
**Return**: Created `User` object without `password` field.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X POST -d '{"nickName":"New_user","email":"newUser@email.com","password":"password",
"regular":true}' "http://localhost:8080/api/admin/users"`

#### 12. Update existing user
**Description**: Update an existing `User` object by `id`.  
**Method**: `PUT`  
**URL**: `http://localhost:8080/api/admin/users/{id}`  
**Path variables**:
  + **`id`** - the field value `id` of the `User` object.

**Request body**: `User` object. Ignored fields: `id`.  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X PUT -d '{"nickName":"Existing_user","email":"existingUser@email.com"}'
"http://localhost:8080/api/admin/users/1"`

#### 13. Delete single user
**Description**: Delete a `User` object by `id` when `id` is not equal to the authorized `User.id`.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/users/{id}`  
**Path variables**:
  + **`id`** - the field value `id` of the `User` object.

**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/users/1"`

#### 14. Delete all users
**Description**: Delete all `User` objects without the authorized `User` object.  
**Method**: `DELETE`  
**URL**: `http://localhost:8080/api/admin/users`  
**Example**: `$ curl --user admin:password -H "Content-Type: application/json" 
-X DELETE "http://localhost:8080/api/admin/users"`

### JSON transfer objects
#### 1. `Page`
**Fields**:
  + **`content`** - array of transfer objects. Value type: `Array`
  + **`current`** - number of current page. Value type: `number`.
  + **`size`** - maximum number of transfer objects. Value type: `number`
  + **`total`** - total pages. Value type: `number`.
#### 2. `Restaurant`
**Fields**:
  + **`id`** - primary key. Value type: `number`.
  + **`name`** - restaurant name. Value type: `string`.
  + **`menu`** - array of `MenuEntry` objects. Value type: `Array`
  + **`rating`** - restaurant rating. Value type: `number`.
#### 3. `MenuEntry`
**Fields**:
  + **`id`** - primary key. Value type: `number`.
  + **`name`** - menu entry name. Value type: `string`.
  + **`price`** - dish price. Value type: `number`.
  + **`date`** - menu entry date. Value type: `string` in ISO date format.
#### 4. `VoteEntry`
**Fields**:
  + **`resturant`** - associated `Restaurant` object. Value type: `Restaurant`.
  + **`dateTime`** - menu entry date. Value type: `string` in ISO date and time format.
#### 5. `User`
**Fields**:
  + **`id`** - primary key. Value type: `number`.
  + **`nickName`** - user nickname. Value type: `string`.
  + **`firstName`** - user first name. Value type: `string`.
  + **`lastName`** - user last name. Value type: `string`.
  + **`email`** - user email. Value type: `string`.
  + **`password`** - user password. Value type: `string`.
  + **`admin`** - administrator sign. Value type: `boolean`.
  + **`regular`** - regular user sign. Value type: `boolean`.