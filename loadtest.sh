#!/bin/bash

# List of product IDs to add to cart (customize as needed)
PRODUCT_IDS=(10097 10079 10324 10102 10301 10283 10319)

# Number of concurrent sessions
NUM_SESSIONS=10000

# Function to simulate a shopping session
shop_session() {
  SESSION_ID=$1
  COOKIE_FILE="cookies_${SESSION_ID}.txt"
  PRODUCT_ID=${PRODUCT_IDS[$((SESSION_ID % ${#PRODUCT_IDS[@]}))]}

  echo "Session $SESSION_ID: Adding product $PRODUCT_ID to cart"
  curl -s -c $COOKIE_FILE -X POST "http://localhost:9100/api/cart/add/$PRODUCT_ID"

  echo "Session $SESSION_ID: Viewing cart"
  curl -s -b $COOKIE_FILE "http://localhost:9100/api/cart"

  echo "Session $SESSION_ID: Checking out"
  curl -s -b $COOKIE_FILE -X POST "http://localhost:9100/api/checkout" \
    -d "name=User$SESSION_ID" \
    -d "shippingAddress=123 Main St, Apt $SESSION_ID" \
    -d "billingAddress=456 Elm St, Apt $SESSION_ID"

  # Clean up cookie file
  rm -f $COOKIE_FILE
}

# Run sessions concurrently
for ((i=0; i<$NUM_SESSIONS; i++)); do
  shop_session $i &
done

wait
echo "All sessions completed."

# #!/bin/bash

# #!/bin/bash

# # Use gdate on macOS for millisecond precision, else fallback to date
# if command -v gdate > /dev/null; then
#   DATE_CMD="gdate"
# else
#   DATE_CMD="date"
# fi

# PRODUCT_IDS=(10097 10079 10324 10102 10301 10283 10319)
# NUM_SESSIONS=5

# declare -a SESSION_TIMES

# shop_session() {
#   SESSION_ID=$1
#   COOKIE_FILE="cookies_${SESSION_ID}.txt"
#   PRODUCT_ID=${PRODUCT_IDS[$((SESSION_ID % ${#PRODUCT_IDS[@]}))]}

#   START_TIME=$(${DATE_CMD} +%s%3N)

#   echo "Session $SESSION_ID: Adding product $PRODUCT_ID to cart"
#   curl -s -c $COOKIE_FILE -X POST "http://localhost:9989/api/cart/add/$PRODUCT_ID" > /dev/null

#   echo "Session $SESSION_ID: Viewing cart"
#   curl -s -b $COOKIE_FILE "http://localhost:9989/api/cart" > /dev/null

#   echo "Session $SESSION_ID: Checking out"
#   curl -s -b $COOKIE_FILE -X POST "http://localhost:9989/api/checkout" \
#     -d "name=User$SESSION_ID" \
#     -d "shippingAddress=123 Main St, Apt $SESSION_ID" \
#     -d "billingAddress=456 Elm St, Apt $SESSION_ID" > /dev/null

#   END_TIME=$(${DATE_CMD} +%s%3N)
#   DURATION=$((END_TIME - START_TIME))
#   SESSION_TIMES[$SESSION_ID]=$DURATION

#   echo "Session $SESSION_ID: Order placed in ${DURATION} ms"

#   rm -f $COOKIE_FILE
# }

# export -f shop_session
# export PRODUCT_IDS

# TOTAL_START=$(${DATE_CMD} +%s%3N)

# for ((i=0; i<$NUM_SESSIONS; i++)); do
#   bash -c "shop_session $i" &
# done

# wait

# TOTAL_END=$(${DATE_CMD} +%s%3N)
# TOTAL_DURATION=$((TOTAL_END - TOTAL_START))

# echo ""
# echo "Order Placement Report:"
# for ((i=0; i<$NUM_SESSIONS; i++)); do
#   echo "  Session $i: ${SESSION_TIMES[$i]:-N/A} ms"
# done
# echo "Total time for all sessions: ${TOTAL_DURATION} ms"